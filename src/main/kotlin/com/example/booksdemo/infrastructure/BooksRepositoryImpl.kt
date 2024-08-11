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

@Repository
class BooksRepositoryImpl(
    private val dslContext: DSLContext,
) : BooksRepository {
    override fun findAll(): List<BooksRecord> {
        return this.dslContext.select()
            .from(BOOKS)
            .fetch()
            .into(BooksRecord::class.java)
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
}
