package com.deepak.course.demo.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class CourseDTO(
    val id: Int?,
    @get:NotBlank(message = "Course name should not be blank")
    val name: String,
    @get:NotBlank(message = "Course category should not be blank")
    val category: String,
    @get:NotNull(message = "Instructor id should not be null")
    val instructorId:Int?= null
)