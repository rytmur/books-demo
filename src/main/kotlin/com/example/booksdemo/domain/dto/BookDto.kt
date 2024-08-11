package com.example.booksdemo.domain.dto

data class BookDto(
    val bookId: Int,
    val title: String,
    val authorId: Int?,
    val authorName: String?,
)
