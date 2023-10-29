package ga.rugal.reactor.core.service

import ga.rugal.UnitTestBase
import ga.rugal.reactor.core.dao.CourseDao
import ga.rugal.reactor.core.dao.RegistrationDao
import ga.rugal.reactor.core.entity.Course
import ga.rugal.reactor.springmvc.exception.CourseNotFoundException
import ga.rugal.reactor.springmvc.exception.CourseReferenceException
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

class CourseServiceTest : UnitTestBase() {

  @MockK
  lateinit var dao: CourseDao

  @MockK
  lateinit var registrationDao: RegistrationDao

  @MockK
  lateinit var registrationService: RegistrationService

  @InjectMockKs
  lateinit var service: CourseService

  private val u = Course(
    id = 1,
    name = "Rugal",
  )

  @BeforeEach
  fun setup() {
    every { registrationService.dao } returns registrationDao
  }

  //<editor-fold defaultstate="collapsed" desc="findById">
  @Test
  fun findById_ok() {
    every { dao.findById(1) } returns Mono.just(u)

    val result = this.service.findById(1)

    StepVerifier
      .create(result)
      .assertNext { Assertions.assertEquals(1, it.id) }
      .verifyComplete()

    verify(exactly = 1) { dao.findById(1) }
  }

  @Test
  fun findById_error() {
    every { dao.findById(1) } returns Mono.empty()

    val result = this.service.findById(1)

    StepVerifier
      .create(result)
      .expectError(CourseNotFoundException::class.java)
      .verify()

    verify(exactly = 1) { dao.findById(1) }
  }
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="deleteById">
  @Test
  fun deleteById_ok() {
    every { registrationDao.existsByCourseId(any()) } returns Mono.just(false)
    every { dao.deleteById(1) } returns Mono.empty()

    val result = this.service.deleteById(1)

    StepVerifier
      .create(result)
      .expectNextMatches { it == true }
      .verifyComplete()

    verify(exactly = 1) { registrationDao.existsByCourseId(any()) }
    verify(exactly = 1) { dao.deleteById(1) }
  }

  @Test
  fun deleteById_error() {
    every { registrationDao.existsByCourseId(any()) } returns Mono.just(true)

    val result = this.service.deleteById(1)

    StepVerifier
      .create(result)
      .expectError(CourseReferenceException::class.java)
      .verify()

    verify(exactly = 1) { registrationDao.existsByCourseId(any()) }
  }
  //</editor-fold>
}
