package com.deepak.course.demo.repository

import com.deepak.course.demo.entity.Course
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface CourseRepository : CrudRepository<Course, Int> {

    fun findByName(courseName:String): List<Course>

    @Query(value = "SELECT * FROM COURSES where name like %?1%", nativeQuery = true)
    fun findByCourseName(courseName: String): List<Course>

}