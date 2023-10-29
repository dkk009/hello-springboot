package com.deepak.course.demo.dto

import jakarta.validation.constraints.NotBlank

data class InstructorDTO (
    val id:Int?,
    @get:NotBlank(message = "Name filed should not be blank")
    val name:String,
)