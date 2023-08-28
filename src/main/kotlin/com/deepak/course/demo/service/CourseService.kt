package com.deepak.course.demo.service

import com.deepak.course.demo.dto.CourseDTO
import com.deepak.course.demo.entity.Course
import com.deepak.course.demo.exception.EntityNotFoundException
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

    fun getAllCourses(courseName: String?): List<CourseDTO> {
        return courseName?.let {
            repository.findByCourseName(courseName).map {
                CourseDTO(it.id, it.name, it.category)
            }
        } ?: kotlin.run {
            repository.findAll().map {
                CourseDTO(it.id, it.name, it.category)
            }
        }
    }

    fun updateCourse(courseId: Int, courseDTO: CourseDTO): CourseDTO {
        val course = repository.findById(courseId)
        if (course.isPresent) {
            val courseEntity = courseDTO.let {
                Course(id = courseId, name = courseDTO.name, category = courseDTO.category)
            }
            repository.save(courseEntity)
            return courseEntity.let {
                CourseDTO(id = courseEntity.id, name = courseEntity.name, category = courseEntity.category)
            }
        } else {
            throw EntityNotFoundException(resourceId = courseId)
        }
    }

    fun getCourse(courseId: Int): CourseDTO {
        val course = repository.findById(courseId)
        if (course.isPresent) {
            return CourseDTO(id = course.get().id, name = course.get().name, category = course.get().category)
        } else {
            throw EntityNotFoundException(resourceId = courseId)
        }
    }

    fun deleteCourse(courseId: Int) {
        val course = repository.findById(courseId)
        if (course.isPresent) {
            repository.delete(course.get())
        } else {
            throw EntityNotFoundException(resourceId = courseId)
        }
    }
}