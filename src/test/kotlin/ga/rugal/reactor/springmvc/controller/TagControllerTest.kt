package ga.rugal.reactor.springmvc.controller

import ga.rugal.reactor.core.entity.Tag
import ga.rugal.reactor.core.service.TagService
import ga.rugal.reactor.springmvc.exception.TagNotFoundException
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import reactor.core.publisher.Mono

@WebFluxTest(TagController::class)
class TagControllerTest : ControllerUnitTestBase() {

  @MockkBean
  lateinit var service: TagService

  private val tag = Tag(1, "Rugal")

  @BeforeEach
  fun setup() {

  }

  @Test
  fun test_found() {
    every { service.findById(any()) } returns Mono.just(tag)

    this.client
      .get()
      .uri("/tag/1")
      .exchange()
      .expectStatus().isOk
      .expectBody()
      .jsonPath("$.name").isEqualTo(tag.name)
      .jsonPath("$.id").isEqualTo(tag.id)

    verify(exactly = 1) { service.findById(any()) }
  }

  @Test
  fun test_not_found() {
    every { service.findById(any()) } returns Mono.error { TagNotFoundException(tag.id) }

    this.client
      .get()
      .uri("/tag/1")
      .exchange()
      .expectStatus().isNotFound

    verify(exactly = 1) { service.findById(any()) }
  }
}
