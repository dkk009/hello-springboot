package com.deepak.course.demo.repository

import com.deepak.course.demo.entity.Instructor
import org.springframework.data.repository.CrudRepository

interface InstructorRepository: CrudRepository<Instructor,Int>