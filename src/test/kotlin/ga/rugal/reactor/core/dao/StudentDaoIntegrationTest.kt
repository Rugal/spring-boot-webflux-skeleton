package ga.rugal.reactor.core.dao

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import reactor.test.StepVerifier

class StudentDaoIntegrationTest(@Autowired val dao: StudentDao) : DaoIntegrationTestBase() {

  @Test
  fun findAll_ok() {
    StepVerifier
      .create(this.dao.findAll())
      .expectNextCount(5)
      .verifyComplete()
  }

  @Test
  fun findById_ok() {
    StepVerifier
      .create(this.dao.findById(1))
      .assertNext { Assertions.assertEquals("Orochi", it.name) }
      .verifyComplete()
  }
}
