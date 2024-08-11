package com.example.booksdemo.controller.v1

import com.example.booksdemo.controller.v1.request.BookSearchQuery
import com.example.booksdemo.domain.dto.BookDto
import com.example.booksdemo.domain.service.BookService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

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
}
