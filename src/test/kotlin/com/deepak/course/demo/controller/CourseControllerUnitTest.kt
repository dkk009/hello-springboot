package com.deepak.course.demo.controller

import com.deepak.course.demo.dto.CourseDTO
import com.deepak.course.demo.service.CourseService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.just
import io.mockk.runs
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.reactive.server.WebTestClient

@WebMvcTest(controllers = [CourseController::class])
@AutoConfigureWebTestClient
class CourseControllerUnitTest {
    @Autowired
    lateinit var webTestClient: WebTestClient

    @MockkBean
    lateinit var courseServiceMokk: CourseService

    @Test
    fun addCourseTest() {
        val courseDTO = CourseDTO(id = null, category = "category", name = "name")
        every { courseServiceMokk.addCourse(any()) } returns getCourseDTO(id = 1)
        val remoteData = webTestClient.post().uri("/v1/course").bodyValue(courseDTO).exchange()
            .expectStatus().is2xxSuccessful.expectBody(CourseDTO::class.java).returnResult().responseBody
        Assertions.assertTrue(remoteData?.id == 1)
    }


    @Test
    fun getCourseTest() {
        every { courseServiceMokk.getCourse(any()) } returns getCourseDTO(id = 1)
        val remoteData = webTestClient.get().uri("/v1/course/1").exchange()
            .expectStatus().is2xxSuccessful.expectBody(CourseDTO::class.java).returnResult().responseBody
        Assertions.assertTrue(remoteData?.id == 1)
    }

    @Test
    fun getCourseListTest() {
        every { courseServiceMokk.getAllCourses() }.returnsMany(listOf<CourseDTO>(getCourseDTO(id = 1)))
        val remoteData = webTestClient.get().uri("/v1/course").exchange()
            .expectStatus().is2xxSuccessful.expectBodyList(CourseDTO::class.java).returnResult().responseBody
        Assertions.assertTrue(remoteData?.first()?.id == 1)
    }

    @Test
    fun updateCourse() {
        val courseDTO = CourseDTO(id = null, category = "category", name = "name")
        every { courseServiceMokk.updateCourse(any(), any()) } returns getCourseDTO(id = 1, name = "updated name")
        val remoteData = webTestClient.put().uri("/v1/course/1").bodyValue(courseDTO).exchange()
            .expectStatus().is2xxSuccessful.expectBody(CourseDTO::class.java).returnResult().responseBody
        Assertions.assertTrue(remoteData?.name == "updated name")
    }

    @Test
    fun deleteCourse() {
        every { courseServiceMokk.deleteCourse(any()) } just runs
        webTestClient.delete().uri("/v1/course/1").exchange().expectStatus().isNoContent
    }

    private fun getCourseDTO(id: Int? = null, category: String = "category", name: String = "name"): CourseDTO {
        return CourseDTO(id, name, category)
    }
}