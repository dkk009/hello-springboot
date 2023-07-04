package com.deepak.course.demo.controller


import com.deepak.course.demo.service.GreetingsService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody

@WebMvcTest(controllers = [GreetingController::class])
@AutoConfigureWebTestClient
class GreetingControllerUnitTest {
    @Autowired
    lateinit var webTestClient: WebTestClient

    @MockkBean
    lateinit var greetingsServiceMockk: GreetingsService

    @Test
    fun retrieveGreeting() {
        val name = "Deepak"
        every { greetingsServiceMockk.retrieveGreeting(any()) } returns "Hello $name, Hello from default profile"
        val result = webTestClient.get().uri("/v1/greetings/{name}", name).exchange()
            .expectStatus().is2xxSuccessful.expectBody<String>().returnResult()
        println("Result: ${result.responseBody}, Mockk: ${greetingsServiceMockk.retrieveGreeting("")}")
        Assertions.assertEquals("Hello $name, Hello from default profile", result.responseBody)
       // Assertions.assertEquals(name, "Deepak")
    }
}