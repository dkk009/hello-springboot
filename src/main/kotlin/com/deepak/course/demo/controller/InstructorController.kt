package com.deepak.course.demo.controller

import com.deepak.course.demo.dto.InstructorDTO
import com.deepak.course.demo.service.InstructorService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("v1/instructor")
@Validated
class InstructorController constructor(private val instructorService: InstructorService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addInstructor(@Valid @RequestBody instructorDTO: InstructorDTO): InstructorDTO{
        return instructorService.addInstructor(instructorDTO)
    }

    @GetMapping
    fun getAllInstructor(): List<InstructorDTO>{
        return instructorService.getInstructor(null)
    }
    @GetMapping("/{id}")
    fun getInstructor(@PathVariable("id") id:Int): List<InstructorDTO>{
        return instructorService.getInstructor(id)
    }

}