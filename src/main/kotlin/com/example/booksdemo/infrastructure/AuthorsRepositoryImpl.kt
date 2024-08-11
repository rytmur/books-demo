package com.example.booksdemo.infrastructure

import com.example.booksdb.jooq.gen.tables.records.AuthorsRecord
import com.example.booksdb.jooq.gen.tables.references.AUTHORS
import com.example.booksdemo.domain.repository.AuthorsRepository
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

@Repository
class AuthorsRepositoryImpl(
    private val dslContext: DSLContext,
) : AuthorsRepository {
    override fun findById(authorId: Int): AuthorsRecord? {
        return this.dslContext.select()
            .from(AUTHORS)
            .where(AUTHORS.AUTHOR_ID.eq(authorId))
            .fetchOne()
            ?.into(AuthorsRecord::class.java)
    }

    override fun insert(name: String): AuthorsRecord {
        return this.dslContext.insertInto(AUTHORS)
            .set(AUTHORS.NAME, name)
            .returning()
            .fetchOne()
            ?: throw RuntimeException("Failed to insert into authors")
    }
}
