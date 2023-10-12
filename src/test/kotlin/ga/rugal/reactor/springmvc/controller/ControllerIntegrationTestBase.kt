package ga.rugal.reactor.springmvc.controller

import ga.rugal.IntegrationTestBase
import ga.rugal.Main
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = [Main::class])
abstract class ControllerIntegrationTestBase : IntegrationTestBase() {
  @Autowired
  lateinit var client: WebTestClient
}
