package com.deepak.course.demo.entity

import jakarta.persistence.*


@Entity
@Table(name = "courses")
data class Course(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int?,
    val name: String,
    val category: String,
    @JoinColumn(name = "instructor_id", nullable = false)
    @ManyToOne(
        fetch = FetchType.LAZY
    )
    val instructor: Instructor? = null
)