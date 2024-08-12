package com.example.booksdemo.infrastructure

import com.example.booksdb.jooq.gen.tables.records.BooksRecord
import com.example.booksdb.jooq.gen.tables.references.AUTHORS
import com.example.booksdb.jooq.gen.tables.references.BOOKS
import com.example.booksdemo.domain.dto.BookDto
import com.example.booksdemo.domain.repository.BooksRepository
import org.jooq.Condition
import org.jooq.DSLContext
import org.jooq.impl.DSL
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class BooksRepositoryImpl(
    private val dslContext: DSLContext,
) : BooksRepository {

    override fun findById(bookId: Int): BooksRecord? {
        return this.dslContext.select()
            .from(BOOKS)
            .where(BOOKS.BOOK_ID.eq(bookId))
            .fetchOne()
            ?.into(BooksRecord::class.java)
    }

    override fun findByCondition(title: String?, authorId: Int?): List<BookDto> {
        val conditions = mutableListOf<Condition>()

        title?.let { conditions.add(BOOKS.TITLE.likeRegex(title)) }
        authorId?.let { conditions.add(BOOKS.AUTHOR_ID.eq(authorId)) }

        val condition = conditions.fold(DSL.noCondition()) { acc, current -> acc.and(current) }

        return this.dslContext.select()
            .from(BOOKS)
            .innerJoin(AUTHORS).on(AUTHORS.AUTHOR_ID.eq(BOOKS.AUTHOR_ID))
            .where(condition)
            .orderBy(BOOKS.BOOK_ID.desc())
            .fetch()
            .map {
                BookDto(
                    bookId = it[BOOKS.BOOK_ID]!!,
                    title = it[BOOKS.TITLE]!!,
                    authorId = it[BOOKS.AUTHOR_ID],
                    authorName = it[AUTHORS.NAME],
                )
            }
    }

    override fun insert(title: String, authorId: Int): BooksRecord {
        return this.dslContext.insertInto(BOOKS)
            .set(BOOKS.TITLE, title)
            .set(BOOKS.AUTHOR_ID, authorId)
            .returning()
            .fetchOne()
            ?: throw RuntimeException("Failed to insert into books")
    }

    override fun update(bookId: Int, title: String, authorId: Int): Int {
        return this.dslContext.update(BOOKS)
            .set(BOOKS.TITLE, title)
            .set(BOOKS.AUTHOR_ID, authorId)
            .set(BOOKS.UPDATED_AT, LocalDateTime.now())
            .where(BOOKS.BOOK_ID.eq(bookId))
            .execute()
    }
}
