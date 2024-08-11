package com.example.booksdemo.controller

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    val logger: Logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

    @ExceptionHandler(IllegalArgumentException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleIllegalArgumentException(ex: IllegalArgumentException): ResponseEntity<Map<String, String>> {
        logger.debug(ex.stackTraceToString())
        return ResponseEntity(
            mapOf("message" to (ex.message ?: "System Error")), HttpStatus.BAD_REQUEST
        )
    }

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleException(ex: Exception): ResponseEntity<Map<String, String>> {
        logger.error(ex.stackTraceToString())
        return ResponseEntity(
            mapOf("message" to "System Error"), HttpStatus.INTERNAL_SERVER_ERROR
        )
    }
}
