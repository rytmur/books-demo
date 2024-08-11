package com.example.booksdemo.controller.v1

import com.example.booksdemo.controller.v1.request.BookEditRequest
import com.example.booksdemo.controller.v1.request.BookSearchQuery
import com.example.booksdemo.domain.dto.BookDto
import com.example.booksdemo.domain.service.BookService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

@RestController
@RequestMapping("/api/v1/books")
class BookController(
    private val bookService: BookService,
) {
    /**
     * 書籍の検索を行う
     *
     * @param query
     * @return
     */
    @GetMapping("search")
    fun searchBooks(@ModelAttribute query: BookSearchQuery): ResponseEntity<List<BookDto>> {
        return ResponseEntity.ok(bookService.searchBooks(query.title, query.authorId))
    }

    /**
     * 書籍の新規登録を行う
     *
     * @param request
     * @return
     */
    @PostMapping("")
    fun addBook(@RequestBody request: BookEditRequest): ResponseEntity<BookDto> {
        val book = bookService.addBook(request.title, request.authorId, request.authorName)

        val location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(book.bookId)
            .toUri()

        return ResponseEntity.created(location).body(book)
    }

    /**
     * 書籍情報の修正を行う
     *
     * @param request
     * @return
     */
    @PutMapping("{bookId}")
    fun editBook(
        @PathVariable bookId: Int,
        @RequestBody request: BookEditRequest,
    ): ResponseEntity<BookDto> {
        return ResponseEntity.ok(
            bookService.editBook(bookId, request.title, request.authorId, request.authorName)
        )
    }
}
