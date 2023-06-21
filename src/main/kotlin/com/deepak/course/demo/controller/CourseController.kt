package com.deepak.course.demo.controller

import com.deepak.course.demo.dto.CourseDTO
import com.deepak.course.demo.service.CourseService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/course")
class CourseController  constructor(private val courseService: CourseService){

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addCourse(@RequestBody courseDTO: CourseDTO): CourseDTO{
        return courseService.addCourse(courseDTO)
    }
}