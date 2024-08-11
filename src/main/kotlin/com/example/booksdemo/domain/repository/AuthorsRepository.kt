package com.example.booksdemo.domain.repository

import com.example.booksdb.jooq.gen.tables.records.AuthorsRecord

interface AuthorsRepository {
    fun findById(authorId: Int): AuthorsRecord?

    fun insert(name: String): AuthorsRecord
}
