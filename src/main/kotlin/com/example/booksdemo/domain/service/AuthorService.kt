package com.example.booksdemo.domain.service

import com.example.booksdemo.domain.dto.BookDto
import com.example.booksdemo.domain.repository.BooksRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthorService(
    private val booksRepository: BooksRepository,
) {
    /**
     * 著者に紐づく書籍情報一覧を取得する
     *
     * @param authorId 著者ID
     * @return 取得した書籍情報一覧
     */
    @Transactional(readOnly = true)
    fun getAuthorBooks(authorId: Int): List<BookDto> {
        return booksRepository.findByAuthorId(authorId)
    }
}
