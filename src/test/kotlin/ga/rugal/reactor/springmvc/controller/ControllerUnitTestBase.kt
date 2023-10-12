package ga.rugal.reactor.springmvc.controller

import ga.rugal.UnitTestBase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.reactive.server.WebTestClient

abstract class ControllerUnitTestBase : UnitTestBase() {
  @Autowired
  lateinit var client: WebTestClient
}
