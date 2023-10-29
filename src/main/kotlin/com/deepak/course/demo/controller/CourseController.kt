package com.deepak.course.demo.controller

import com.deepak.course.demo.dto.CourseDTO
import com.deepak.course.demo.service.CourseService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/course")
@Validated
class CourseController constructor(
    private val courseService: CourseService,
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addCourse(@Valid @RequestBody courseDTO: CourseDTO): CourseDTO {
        return courseService.addCourse(courseDTO)
    }

    @GetMapping
    fun getCourse(@RequestParam("courseName", required = false) courseName: String?): List<CourseDTO> {
        return courseService.getAllCourses(courseName)
    }

    @PutMapping("/{courseId}")
    fun updateCourse(@PathVariable courseId: Int, @RequestBody courseDTO: CourseDTO): CourseDTO {
        return courseService.updateCourse(courseId, courseDTO)
    }

    @GetMapping("/{courseId}")
    fun getCourse(@PathVariable("courseId") courseId: Int): CourseDTO {
        return courseService.getCourse(courseId)
    }

    @DeleteMapping("/{courseId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteCourse(@PathVariable("courseId") courseId: Int) {
        courseService.deleteCourse(courseId)
    }
}