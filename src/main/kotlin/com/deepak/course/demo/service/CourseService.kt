package com.deepak.course.demo.service

import com.deepak.course.demo.dto.CourseDTO
import com.deepak.course.demo.entity.Course
import com.deepak.course.demo.repository.CourseRepository
import mu.KLogging
import org.springframework.stereotype.Service

@Service
class CourseService constructor(private val repository: CourseRepository) {

    companion object : KLogging()
    fun addCourse(courseDTO: CourseDTO): CourseDTO {
        val courseEntity = courseDTO.let {
            Course(id = null, name = it.name, category = it.category)
        }
        repository.save(courseEntity)
        logger.info("Saved course is: $courseEntity")
        return courseEntity.let {
            CourseDTO(id = it.id, name = it.name, category = it.category)
        }
    }
}