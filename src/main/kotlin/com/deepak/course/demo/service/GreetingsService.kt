package com.deepak.course.demo.service

import mu.KLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class GreetingsService {
    @Value("\${message}")
    lateinit var message:String

    companion object: KLogging()
    fun retrieveGreeting(name:String):String{
        logger.info("Name is $name")
        return "Hello $name, $message"
    }
}