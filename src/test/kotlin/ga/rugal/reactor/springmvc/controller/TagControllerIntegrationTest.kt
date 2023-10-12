package ga.rugal.reactor.springmvc.controller

import org.junit.jupiter.api.Test

class TagControllerIntegrationTest : ControllerIntegrationTestBase() {

  @Test
  fun test() {
    this.client
      .get()
      .uri("/tag")
      .exchange()
      .expectStatus().isOk
      .expectBody()
      .jsonPath("$.name").isEqualTo("BlueRay")
      .jsonPath("$.id").isEqualTo(1)
  }
}
