package ga.rugal.reactor.springmvc.controller

import ga.rugal.reactor.core.dao.TagDao
import ga.rugal.reactor.core.entity.Tag
import ga.rugal.reactor.core.service.TagService
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
  lateinit var dao: TagDao

  @MockkBean
  lateinit var service: TagService

  @BeforeEach
  fun setup() {
    every { service.tagDao } returns dao
  }

  @Test
  fun test() {
    every { dao.findById(1) } returns Mono.just(Tag(1, "Rugal"))

    this.client
      .get()
      .uri("/tag")
      .exchange()
      .expectStatus().isOk
      .expectBody()
      .jsonPath("$.name").isEqualTo("Rugal")
      .jsonPath("$.id").isEqualTo(1)

    verify(exactly = 1) { dao.findById(1) }
  }
}
