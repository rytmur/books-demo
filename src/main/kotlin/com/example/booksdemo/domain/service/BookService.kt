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
    /**
     * 書籍を検索する
     *
     * @param title 書籍タイトル（部分一致）
     * @param authorId 著者ID
     * @return 取得した書籍情報一覧
     */
    @Transactional(readOnly = true)
    fun searchBooks(title: String?, authorId: Int?): List<BookDto> {
        return booksRepository.findByCondition(title, authorId)
    }

    /**
     * 書籍を新規登録する
     *
     * @param title 書籍タイトル
     * @param authorId 著者ID
     * @param authorName 著者名（著者も新規登録する場合に使用する）
     * @return 書籍情報
     */
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

    /**
     * 書籍の詳細情報を取得する
     *
     * @param bookId 書籍ID
     * @return 書籍情報
     */
    @Transactional(readOnly = true)
    @Throws(IllegalArgumentException::class)
    fun getBookDetail(bookId: Int): BookDto {
        return booksRepository.findByBookId(bookId)?.let { booksRecord ->
            BookDto(
                bookId = booksRecord.bookId!!,
                title = booksRecord.title,
                authorId = booksRecord.authorId,
                authorName = authorsRepository.findById(booksRecord.authorId)?.name,
            )
        } ?: throw IllegalArgumentException("書籍情報が登録されていません")
    }

    /**
     * 書籍情報を更新する
     *
     * @param bookId 書籍ID
     * @param title 書籍タイトル
     * @param authorId 著者ID
     * @param authorName 著者名（著者も新規登録する場合に使用する）
     * @return 書籍情報
     */
    @Transactional(readOnly = false)
    @Throws(IllegalArgumentException::class)
    fun editBook(bookId: Int, title: String, authorId: Int?, authorName: String?): BookDto {
        if (title.isBlank()) {
            throw IllegalArgumentException("書籍のタイトルが入力されていません")
        }

        val authorsRecord: AuthorsRecord

        if (authorId != null) {
            authorsRecord = authorsRepository.findById(authorId)
                ?: throw IllegalArgumentException("著者情報が登録されていません")
            if (booksRepository.update(bookId, title, authorId) != 1) {
                throw IllegalArgumentException("書籍情報が登録されていません")
            }
        } else {
            if (authorName.isNullOrBlank()) {
                throw IllegalArgumentException("著者の名前が入力・選択されていません")
            }

            authorsRecord = authorsRepository.insert(authorName)
            if (booksRepository.update(bookId, title, authorsRecord.authorId!!) != 1) {
                throw IllegalArgumentException("書籍情報が登録されていません")
            }
        }

        return BookDto(
            bookId = bookId,
            title = title,
            authorId = authorsRecord.authorId,
            authorName = authorsRecord.name,
        )
    }
}
