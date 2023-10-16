package ga.rugal.reactor.core.service

import ga.rugal.IntegrationTestBase
import ga.rugal.reactor.core.dao.TagDao
import ga.rugal.reactor.core.entity.Tag
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

class TagServiceIntegrationTest(@Autowired private val service: TagService) : IntegrationTestBase() {

  @Test
  fun test_one() {
    val result = this.service.findById(1)

    StepVerifier
      .create(result)
      .assertNext { Assertions.assertEquals(1, it.id) }
      .verifyComplete()
  }

  @Test
  fun test_error() {
    val result = this.service.findById(0)

    StepVerifier
      .create(result)
      .expectError()
      .verify()
  }
}
