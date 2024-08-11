package com.example.booksdemo.domain.service

import com.example.booksdb.jooq.gen.tables.records.AuthorsRecord
import com.example.booksdb.jooq.gen.tables.records.BooksRecord
import com.example.booksdemo.domain.dto.BookDto
import com.example.booksdemo.domain.repository.AuthorsRepository
import com.example.booksdemo.domain.repository.BooksRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BookService(
    private val booksRepository: BooksRepository,
    private val authorsRepository: AuthorsRepository,
) {
    @Transactional(readOnly = true)
    fun searchBooks(title: String?, authorId: Int?): List<BookDto> {
        return booksRepository.findByCondition(title, authorId)
    }

    @Transactional(readOnly = false)
    @Throws(IllegalArgumentException::class)
    fun addBook(title: String, authorId: Int?, authorName: String?): BookDto {
        if (title.isBlank()) {
            throw IllegalArgumentException("書籍のタイトルが入力されていません")
        }

        val authorsRecord: AuthorsRecord
        val booksRecord: BooksRecord

        if (authorId != null) {
            authorsRecord = authorsRepository.findById(authorId)
                ?: throw IllegalArgumentException("著者情報が登録されていません")
            booksRecord = booksRepository.insert(title, authorId)
        } else {
            if (authorName.isNullOrBlank()) {
                throw IllegalArgumentException("著者の名前が入力・選択されていません")
            }

            authorsRecord = authorsRepository.insert(authorName)
            booksRecord = booksRepository.insert(title, authorsRecord.authorId!!)
        }

        return BookDto(
            bookId = booksRecord.bookId!!,
            title = booksRecord.title,
            authorId = booksRecord.authorId,
            authorName = authorsRecord.name,
        )
    }
}
