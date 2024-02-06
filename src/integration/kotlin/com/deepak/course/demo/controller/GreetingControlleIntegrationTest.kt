package com.deepak.course.demo.controller

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.reactive.server.expectBody
import org.testcontainers.junit.jupiter.Testcontainers

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
class GreetingControllerUIntegrationTest {
    @Autowired
    lateinit var webTestClient: WebTestClient

    @Test
    fun testRetrieverGreeting() {
        val name  = "deepak"
        val result = webTestClient.get().uri("/v1/greetings/{deepak}", name).exchange().expectStatus().is2xxSuccessful.expectBody<String>().returnResult()
        Assertions.assertTrue(result.responseBody?.contains("Hello deepak")?:false)
    }
}