package com.deepak.course.demo.service

import com.deepak.course.demo.dto.InstructorDTO
import com.deepak.course.demo.entity.Instructor
import com.deepak.course.demo.exception.EntityNotFoundException
import com.deepak.course.demo.repository.InstructorRepository
import org.springframework.stereotype.Service

@Service
class InstructorService constructor(private val instructorRepository: InstructorRepository) {

    fun addInstructor(instructorDTO: InstructorDTO): InstructorDTO {
        val instructorEntity = instructorDTO.let {
            Instructor(id = it.id, name = it.name)
        }
        instructorRepository.save(instructorEntity)
        return instructorEntity.let {
            InstructorDTO(id = it.id, name = it.name)
        }
    }

    fun getInstructor(instructorId: Int?): List<InstructorDTO> {
        return instructorId?.let { resourceId ->
            val instructorEntity = instructorRepository.findById(resourceId)
            if (instructorEntity.isPresent) {
                mutableListOf<InstructorDTO>().apply {
                    add(InstructorDTO(id = instructorEntity.get().id, name = instructorEntity.get().name))
                }
            } else {
                throw EntityNotFoundException(resourceId)
            }
        } ?: run {
            instructorRepository.findAll().map {
                InstructorDTO(id = it.id, name = it.name)
            }
        }
    }

    fun isValidInstructorId(id:Int) = instructorRepository.findById(id)
}