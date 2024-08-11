package com.example.booksdemo.domain.repository

import com.example.booksdb.jooq.gen.tables.records.BooksRecord
import com.example.booksdemo.domain.dto.BookDto

interface BooksRepository {
    fun findAll(): List<BooksRecord>

    fun findByCondition(title: String?, authorId: Int?): List<BookDto>
}
