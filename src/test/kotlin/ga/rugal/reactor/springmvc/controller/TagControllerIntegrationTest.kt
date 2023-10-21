package ga.rugal.reactor.springmvc.controller

import org.junit.jupiter.api.Test

class TagControllerIntegrationTest : ControllerIntegrationTestBase() {

  @Test
  fun test_found() {
    this.client
      .get()
      .uri("/tag/1")
      .exchange()
      .expectStatus().isOk
      .expectBody()
      .jsonPath("$.name").isEqualTo("BlueRay")
      .jsonPath("$.id").isEqualTo(1)
  }

  @Test
  fun test_not_found() {
    this.client
      .get()
      .uri("/tag/0")
      .exchange()
      .expectStatus().isNotFound
  }
}
