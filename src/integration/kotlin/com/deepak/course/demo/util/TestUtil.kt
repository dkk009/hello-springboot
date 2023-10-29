package com.deepak.course.demo.util

import com.deepak.course.demo.dto.CourseDTO
import com.deepak.course.demo.dto.InstructorDTO

val courseList = listOf<CourseDTO>(
    CourseDTO(id = null, name = "Test course1", category = "Test1"),
    CourseDTO(id = null, name = "Test course2", category = "Test2"),
    CourseDTO(id = null, name = "Test course3", category = "Test3")
)

val instructorList = listOf(
    InstructorDTO(id = null, name = "Instructor-1"),
    InstructorDTO(id = null, name = "Instructor-2"),
    InstructorDTO(id = null, name = "Instructor-3"),
    InstructorDTO(id = null, name = "Instructor-4")
)