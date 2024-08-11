package com.example.booksdemo.domain.service

import com.example.booksdemo.domain.dto.BookDto
import com.example.booksdemo.domain.repository.BooksRepository
import org.springframework.stereotype.Service

@Service
class BookService(
    private val booksRepository: BooksRepository,
) {
    fun searchBooks(title: String?, authorId: Int?): List<BookDto> {
        return booksRepository.findByCondition(title, authorId)
    }
}
