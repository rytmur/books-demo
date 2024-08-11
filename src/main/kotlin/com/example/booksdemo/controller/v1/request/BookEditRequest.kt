package com.example.booksdemo.controller.v1.request

data class BookEditRequest(
    val title: String,
    val authorId: Int?, // 登録済みの著者を選択する
    val authorName: String?, // 新規著者を登録する
)
