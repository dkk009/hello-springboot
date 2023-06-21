package com.deepak.course.demo.controller

import com.deepak.course.demo.service.GreetingsService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/greetings")
class GreetingController (private val greetingsService: GreetingsService) {

    @GetMapping("/{name}")
    fun retrieveGreeting(@PathVariable("name") name:String):String{
        return  greetingsService.retrieveGreeting(name)
    }
}