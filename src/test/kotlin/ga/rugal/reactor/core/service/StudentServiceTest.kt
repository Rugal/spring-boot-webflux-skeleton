package ga.rugal.reactor.core.service

import ga.rugal.UnitTestBase
import ga.rugal.reactor.core.dao.RegistrationDao
import ga.rugal.reactor.core.dao.StudentDao
import ga.rugal.reactor.core.entity.Student
import ga.rugal.reactor.springmvc.exception.StudentNotFoundException
import ga.rugal.reactor.springmvc.exception.StudentReferenceException
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

class StudentServiceTest : UnitTestBase() {

  @MockK
  lateinit var dao: StudentDao

  @MockK
  lateinit var registrationDao: RegistrationDao

  @MockK
  lateinit var registrationService: RegistrationService

  @InjectMockKs
  lateinit var service: StudentService

  private val u = Student(
    id = 1,
    name = "Rugal",
  )

  @BeforeEach
  fun setup() {
    every { registrationService.dao } returns this.registrationDao
  }

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
      .expectError(StudentNotFoundException::class.java)
      .verify()

    verify(exactly = 1) { dao.findById(1) }
  }

  @Test
  fun deleteById_ok() {
    every { registrationDao.existsByStudentId(any()) } returns Mono.just(false)
    every { dao.deleteById(1) } returns Mono.empty()

    val result = this.service.deleteById(1)

    StepVerifier
      .create(result)
      .expectNextMatches { it == true }
      .verifyComplete()

    verify(exactly = 1) { registrationDao.existsByStudentId(any()) }
    verify(exactly = 1) { dao.deleteById(1) }
  }

  @Test
  fun deleteById_error() {
    every { registrationDao.existsByStudentId(any()) } returns Mono.just(true)

    val result = this.service.deleteById(1)

    StepVerifier
      .create(result)
      .expectError(StudentReferenceException::class.java)
      .verify()

    verify(exactly = 1) { registrationDao.existsByStudentId(any()) }
  }
}
