package ga.rugal.reactor.core.service

import ga.rugal.UnitTestBase
import ga.rugal.r2dbc.graphql.NewRegistrationDto
import ga.rugal.reactor.core.dao.RegistrationDao
import ga.rugal.reactor.core.entity.Course
import ga.rugal.reactor.core.entity.Registration
import ga.rugal.reactor.core.entity.Student
import ga.rugal.reactor.springmvc.exception.CourseNotFoundException
import ga.rugal.reactor.springmvc.exception.RedundantRegistrationException
import ga.rugal.reactor.springmvc.exception.RegistrationNotFoundException
import ga.rugal.reactor.springmvc.exception.StudentNotFoundException
import io.mockk.called
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

class RegistrationServiceTest : UnitTestBase() {

  //<editor-fold defaultstate="collapsed" desc="setup">
  @MockK
  lateinit var dao: RegistrationDao

  @MockK
  lateinit var studentService: StudentService

  @MockK
  lateinit var courseService: CourseService

  @InjectMockKs
  lateinit var service: RegistrationService

  private val u = Registration(
    id = 1,
    score = 100,
    studentId = 1,
    courseId = 1,
  )

  private val s = Student(
    id = 1,
    name = "Rugal",
  )

  private val c = Course(
    id = 1,
    name = "Rugal",
  )

  private val n = NewRegistrationDto(u.studentId!!, u.courseId!!, u.score!!)

  @BeforeEach
  fun setup() {
    every { dao.findByStudentIdAndCourseId(any(), any()) } returns Mono.empty()
    every { dao.save(any()) } returns Mono.just(u)

    every { studentService.findById(any()) } returns Mono.just(s)

    every { courseService.findById(any()) } returns Mono.just(c)
  }
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="findById">
  @Test
  fun findById_one() {
    every { dao.findById(1) } returns Mono.just(u)

    val result = this.service.findById(1)

    StepVerifier
      .create(result)
      .expectNextMatches { 1 == it.id }
      .verifyComplete()

    verify(exactly = 1) { dao.findById(1) }
  }

  @Test
  fun findById_error() {
    every { dao.findById(1) } returns Mono.empty()

    val result = this.service.findById(1)

    StepVerifier
      .create(result)
      .expectError(RegistrationNotFoundException::class.java)
      .verify()

    verify(exactly = 1) { dao.findById(1) }
  }
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="save">
  @Test
  fun `save redundant`() {
    every { dao.findByStudentIdAndCourseId(any(), any()) } returns Mono.just(u)

    val result = this.service.save(n)

    StepVerifier
      .create(result)
      .expectError(RedundantRegistrationException::class.java)
      .verify()

    verify(exactly = 1) { dao.findByStudentIdAndCourseId(any(), any()) }
    verify(exactly = 1) { studentService.findById(any()) }
    verify(exactly = 1) { courseService.findById(any()) }
    verify { dao.save(any()) wasNot called }
  }

  @Test
  fun `save ok`() {
    val result = this.service.save(n)

    StepVerifier
      .create(result)
      .expectNext(u)
      .verifyComplete()

    verify(exactly = 1) { dao.findByStudentIdAndCourseId(any(), any()) }
    verify(exactly = 1) { studentService.findById(any()) }
    verify(exactly = 1) { courseService.findById(any()) }
    verify(exactly = 1) { dao.save(any()) }
  }
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="deleteById">
  @Test
  fun `deleteById not found`() {
    every { dao.findById(u.id) } returns Mono.empty()

    StepVerifier
      .create(this.service.deleteById(u.id))
      .expectNextMatches { it == false }
      .verifyComplete()

    verify(exactly = 1) { dao.findById(u.id) }
    verify { dao.deleteById(u.id) wasNot called }
  }

  @Test
  fun `deleteById found`() {
    every { dao.findById(u.id) } returns Mono.just(u)
    every { dao.deleteById(u.id) } returns Mono.empty()

    StepVerifier
      .create(this.service.deleteById(u.id))
      .expectNextMatches { it == true }
      .verifyComplete()

    verify(exactly = 1) { dao.findById(u.id) }
    verify(exactly = 1) { dao.deleteById(u.id) }
  }
  //</editor-fold>
}
