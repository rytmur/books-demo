package com.example.booksdemo.controller.v1.request

data class BookSearchQuery(
    val title: String?,
    val authorId: Int?, // プルダウン・サジェスト等で選択することを想定
)
