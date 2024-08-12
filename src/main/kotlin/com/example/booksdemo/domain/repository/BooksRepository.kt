package com.example.booksdemo.domain.repository

import com.example.booksdb.jooq.gen.tables.records.BooksRecord
import com.example.booksdemo.domain.dto.BookDto

interface BooksRepository {
    fun findById(bookId: Int): BooksRecord?

    fun findByCondition(title: String?, authorId: Int?): List<BookDto>

    fun findByAuthorId(authorId: Int?): List<BookDto>

    fun insert(title: String, authorId: Int): BooksRecord

    fun update(bookId: Int, title: String, authorId: Int): Int
}
