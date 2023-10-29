package com.deepak.course.demo.exception

import mu.KLogging
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@Component
@ControllerAdvice
class GlobalErrorHandler: ResponseEntityExceptionHandler() {
    companion object: KLogging()

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any>? {
        logger.error("Method argument not valid exception:${ex.message}", ex)
        val error = ex.bindingResult.allErrors.map { error-> error.defaultMessage!! }.sorted()
        logger.info(error)
        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error.joinToString(","){it})
    }

    @ExceptionHandler(InstructorNotFoundException::class)
    fun handleInstructorNotFoundException(exception: InstructorNotFoundException, request: WebRequest): ResponseEntity<Any>{
     return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.message)
    }
    @ExceptionHandler(Exception::class)
    fun handleAllException(ex:Exception,request: WebRequest): ResponseEntity<Any>{
        logger.error("Exception occurred:$ex", ex)
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.message)
    }


}