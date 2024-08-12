package com.example.booksdemo.domain.service

import com.example.booksdemo.domain.dto.BookDto
import com.example.booksdemo.domain.repository.BooksRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthorService(
    private val booksRepository: BooksRepository,
) {
    @Transactional(readOnly = true)
    fun getAuthorBooks(authorId: Int): List<BookDto> {
        return booksRepository.findByAuthorId(authorId)
    }
}
