package com.deepak.course.demo.controller

import com.deepak.course.demo.dto.CourseDTO
import com.deepak.course.demo.entity.Course
import com.deepak.course.demo.repository.CourseRepository
import com.deepak.course.demo.util.courseList
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody
import org.springframework.test.web.reactive.server.expectBodyList

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
class CourseControllerIntegrationTest {
    @Autowired
    lateinit var webTestClient: WebTestClient

    @Autowired
    lateinit var courseRepository: CourseRepository

    @BeforeEach
    fun setUp() {
        courseRepository.deleteAll()
        courseRepository.saveAll(courseList.map {
            Course(name = it.name, category = it.category, id = null)
        })
        println(" I am setup method, I suppose to be called before every test")
    }

    @Test
    fun addCourseTest() {
        val courseDTO = CourseDTO(id = null, name = "Kotlin course", category = "Development")
        val savedCourseDto =
            webTestClient.post().uri("/v1/course").bodyValue(courseDTO).exchange().expectStatus().isCreated.expectBody(
                CourseDTO::class.java
            ).returnResult().responseBody
        Assertions.assertTrue(savedCourseDto!!.id != null)
    }

    @Test
    fun getAllCourseTest() {
        val courseDTO = CourseDTO(id = null, name = "Kotlin course", category = "Development")
        val savedCourseDto =
            webTestClient.post().uri("/v1/course").bodyValue(courseDTO).exchange().expectStatus().isCreated.expectBody(
                CourseDTO::class.java
            ).returnResult().responseBody


        val remoteList = webTestClient.get().uri("/v1/course").exchange().expectStatus().is2xxSuccessful.expectBodyList(
            CourseDTO::class.java
        ).returnResult().responseBody
        println("Course List: ${remoteList!!.size}, List:${remoteList}")
        Assertions.assertTrue(remoteList!!.size == courseList.size + 1)
    }

    @Test
    fun getSingleCourseTest() {
        val remoteCourseList = webTestClient.get().uri("/v1/course").exchange().expectStatus().is2xxSuccessful.expectBodyList<CourseDTO>().returnResult().responseBody
        val id = remoteCourseList?.get(0)?.id?:1
        println("Remote course List:$remoteCourseList")
        val remoteCourse = webTestClient.get().uri("/v1/course/$id").exchange().expectStatus().is2xxSuccessful.expectBody(
            CourseDTO::class.java
        ).returnResult().responseBody
        Assertions.assertTrue(remoteCourse!!.id == id)

    }

    @Test
    fun updateSingleCourseTest() {
        val courseDto = CourseDTO(id = null, name = "Kotlin course", category = "Development")
        val remoteCourseList = webTestClient.get().uri("/v1/course").exchange().expectStatus().is2xxSuccessful.expectBodyList<CourseDTO>().returnResult().responseBody
        val id = remoteCourseList?.get(0)?.id?:1
        val updateCourse = webTestClient.put().uri("/v1/course/$id").bodyValue(courseDto).exchange().expectStatus().is2xxSuccessful.expectBody<CourseDTO>().returnResult().responseBody
        val remoteCourse = webTestClient.get().uri("v1/course/$id").exchange().expectStatus().is2xxSuccessful.expectBody<CourseDTO>().returnResult().responseBody
        Assertions.assertTrue(courseDto.name == updateCourse!!.name)
        Assertions.assertTrue(courseDto.name == remoteCourse!!.name && remoteCourse.id == updateCourse.id)
    }
}