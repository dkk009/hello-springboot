package com.deepak.course.demo.controller

import com.deepak.course.demo.dto.InstructorDTO
import com.deepak.course.demo.service.InstructorService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody

@WebMvcTest(controllers = [InstructorController::class])
@AutoConfigureWebTestClient
class InstructorControllerTest {
    @Autowired
    lateinit var webTestClient: WebTestClient

    @MockkBean
    lateinit var instructorService: InstructorService
    private val INSTRUCTOR_PATH = "/v1/instructor"

    @Test
    fun addInstructorTest() {
        val instructorDTO = InstructorDTO(id = null, name = "Test")
        every { instructorService.addInstructor(instructorDTO) } returns getInstructorDTO(id = 1, name = "Test")
        val resp =
            webTestClient.post().uri(INSTRUCTOR_PATH).bodyValue(instructorDTO).exchange().expectStatus().is2xxSuccessful
                .expectBody(InstructorDTO::class.java).returnResult().responseBody
        assert(resp?.id == 1)
    }

    @Test
    fun getInstructorTest() {
        val instructorDTO = getInstructorDTO(id = 1, name = "test")
        every { instructorService.getInstructor(null) } returns mutableListOf<InstructorDTO>().apply {
            add(instructorDTO)
        }

        val resp = webTestClient.get().uri(INSTRUCTOR_PATH).exchange()
            .expectStatus().is2xxSuccessful.expectBody<List<InstructorDTO>>().returnResult().responseBody
        assert(resp!!.isNotEmpty())
        assert(resp!!.first().id == 1)
    }

    private fun getInstructorDTO(id: Int? = null, name: String = "") = InstructorDTO(id = id, name = name)
}