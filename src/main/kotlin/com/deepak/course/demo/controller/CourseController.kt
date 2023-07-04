package com.deepak.course.demo.controller

import com.deepak.course.demo.dto.CourseDTO
import com.deepak.course.demo.service.CourseService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
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

    @GetMapping
    fun getCourse(): List<CourseDTO>{
        return courseService.getAllCourses()
    }
    @PutMapping("/{courseId}")
    fun updateCourse(@PathVariable courseId:Int, @RequestBody courseDTO: CourseDTO): CourseDTO{
        return  courseService.updateCourse(courseId, courseDTO)
    }

    @GetMapping("/{courseId}")
    fun getCourse(@PathVariable("courseId") courseId: Int): CourseDTO{
        return courseService.getCourse(courseId)
    }
}