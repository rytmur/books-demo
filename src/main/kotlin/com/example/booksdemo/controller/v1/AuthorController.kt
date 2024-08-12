package com.example.booksdemo.controller.v1

import com.example.booksdemo.domain.dto.BookDto
import com.example.booksdemo.domain.service.AuthorService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/authors")
class AuthorController(
    private val authorService: AuthorService,
) {
    /**
     * 著者に紐づく書籍一覧を取得する
     *
     * @param authorId 著者ID
     * @return
     */
    @GetMapping("{authorId}/books")
    fun getAuthorBooks(@PathVariable authorId: Int): ResponseEntity<List<BookDto>> {
        return ResponseEntity.ok(authorService.getAuthorBooks(authorId))
    }
}
