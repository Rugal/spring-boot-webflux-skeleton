package ga.rugal.reactor.core.service

import ga.rugal.UnitTestBase
import ga.rugal.reactor.core.dao.CourseDao
import ga.rugal.reactor.core.entity.Course
import ga.rugal.reactor.springmvc.exception.CourseNotFoundException
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

class CourseServiceTest : UnitTestBase() {

  @MockK
  lateinit var dao: CourseDao

  @InjectMockKs
  lateinit var service: CourseService

  private val u = Course(
    id = 1,
    name = "Rugal",
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
      .expectError(CourseNotFoundException::class.java)
      .verify()

    verify(exactly = 1) { dao.findById(1) }
  }
}
