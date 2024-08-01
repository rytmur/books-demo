package com.example.booksdemo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BooksdemoApplication

fun main(args: Array<String>) {
	runApplication<BooksdemoApplication>(*args)
}
