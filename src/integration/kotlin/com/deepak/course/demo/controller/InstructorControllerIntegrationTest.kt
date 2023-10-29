package com.deepak.course.demo.controller

import com.deepak.course.demo.dto.InstructorDTO
import com.deepak.course.demo.entity.Instructor
import com.deepak.course.demo.repository.InstructorRepository
import com.deepak.course.demo.util.instructorList
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
class InstructorControllerIntegrationTest {
    @Autowired
    lateinit var webTestClient: WebTestClient

    @Autowired
    lateinit var instructorRepository: InstructorRepository

    @BeforeEach
    fun setUp() {
        instructorRepository.deleteAll()
        instructorRepository.saveAll(instructorList.map {
            Instructor(id = it.id, name = it.name)
        })
    }

    @Test
    fun getInstructor() {
              val instructorList =
                  webTestClient.get().uri("/v1/instructor").exchange().expectStatus().is2xxSuccessful.expectBodyList(
                      InstructorDTO::class.java
                  ).returnResult().responseBody

              val id = instructorList!!.first().id
        val response = webTestClient.get().uri("/v1/instructor/$id").exchange()
            .expectStatus().is2xxSuccessful.expectBody<List<InstructorDTO>>().returnResult().responseBody
        assert(response!!.size == 1)
        assert(response!!.first().id == id)
    }

    @Test
    fun getALlInstructor() {
        val response = webTestClient.get().uri("/v1/instructor").exchange()
            .expectStatus().is2xxSuccessful.expectBody<List<InstructorDTO>>().returnResult().responseBody
        assert(response!!.size == instructorList.size)
    }

    @Test
    fun addInstructor() {
        val instructorDTO = InstructorDTO(id = null, name = "new instructor")
        val response = webTestClient.post().uri("/v1/instructor").bodyValue(instructorDTO).exchange()
            .expectStatus().is2xxSuccessful.expectBody<InstructorDTO>().returnResult().responseBody
        assert(response!!.id != null)
    }
}