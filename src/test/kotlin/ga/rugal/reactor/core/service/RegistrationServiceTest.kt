package ga.rugal.reactor.core.service

import ga.rugal.UnitTestBase
import ga.rugal.reactor.core.dao.RegistrationDao
import ga.rugal.reactor.core.entity.Registration
import ga.rugal.reactor.springmvc.exception.RegistrationNotFoundException
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

class RegistrationServiceTest : UnitTestBase() {

  @MockK
  lateinit var dao: RegistrationDao

  @InjectMockKs
  lateinit var service: RegistrationService

  private val u = Registration(
    id = 1,
    score = 100,
    studentId = 1,
    courseId = 1,
  )

  @Test
  fun test_one() {
    every { dao.findById(1) } returns Mono.just(u)

    val result = this.service.findById(1)

    StepVerifier
      .create(result)
      .assertNext { Assertions.assertEquals(1, it.id) }
      .verifyComplete()

    verify(exactly = 1) { dao.findById(1) }
  }

  @Test
  fun test_error() {
    every { dao.findById(1) } returns Mono.empty()

    val result = this.service.findById(1)

    StepVerifier
      .create(result)
      .expectError(RegistrationNotFoundException::class.java)
      .verify()

    verify(exactly = 1) { dao.findById(1) }
  }
}
