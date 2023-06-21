package com.deepak.course.demo.repository

import com.deepak.course.demo.entity.Course
import org.springframework.data.repository.CrudRepository

interface CourseRepository : CrudRepository<Course, Int> {

}