package com.deepak.course.demo.dto

import jakarta.validation.constraints.NotBlank

data class CourseDTO(
    val id: Int?,
    @get:NotBlank(message = "Course name should not be blank")
    val name: String,
    @get:NotBlank(message = "Course category should not be blank")
    val category: String
)