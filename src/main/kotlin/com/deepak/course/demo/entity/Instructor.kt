package com.deepak.course.demo.entity

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank

@Entity
@Table(name = "instructor")
data class Instructor(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int?,
    @NotBlank(message = "Instructor name should not be null or blank")
    val name: String,
    @ManyToMany(
        mappedBy = "instructor",
        cascade = [CascadeType.ALL]
    )
    val course: List<Course> = mutableListOf()
)