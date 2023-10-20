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
import org.junit.jupiter.api.Test
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

class RegistrationServiceTest : UnitTestBase() {

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

  @Test
  fun test_one() {
    every { dao.findById(1) } returns Mono.just(u)

    val result = this.service.findById(1)

    StepVerifier
      .create(result)
      .expectNextMatches { 1 == it.id }
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

  @Test
  fun save_redundant() {
    every { dao.findByStudentIdAndCourseId(any(), any()) } returns Mono.just(u)

    val result = this.service.save(NewRegistrationDto(u.studentId!!, u.courseId!!, u.score!!))

    StepVerifier
      .create(result)
      .expectError(RedundantRegistrationException::class.java)
      .verify()

    verify(exactly = 1) { dao.findByStudentIdAndCourseId(any(), any()) }
    verify { studentService.findById(any()) wasNot called }
    verify { courseService.findById(any()) wasNot called }
    verify { dao.save(any()) wasNot called }
  }

  @Test
  fun save_student_not_found() {
    every { dao.findByStudentIdAndCourseId(any(), any()) } returns Mono.empty()
    every { studentService.findById(any()) } returns Mono.error { StudentNotFoundException(u.studentId!!) }

    val result = this.service.save(NewRegistrationDto(u.studentId!!, u.courseId!!, u.score!!))

    StepVerifier
      .create(result)
      .expectError(StudentNotFoundException::class.java)
      .verify()

    verify(exactly = 1) { dao.findByStudentIdAndCourseId(any(), any()) }
    verify { studentService.findById(any()) wasNot called }
    verify { courseService.findById(any()) wasNot called }
    verify { dao.save(any()) wasNot called }
  }

  @Test
  fun save_course_not_found() {
    every { dao.findByStudentIdAndCourseId(any(), any()) } returns Mono.empty()
    every { studentService.findById(any()) } returns Mono.just(Student(1, "Rugal"))
    every { courseService.findById(any()) } returns Mono.error { CourseNotFoundException(u.courseId!!) }

    val result = this.service.save(NewRegistrationDto(u.studentId!!, u.courseId!!, u.score!!))

    StepVerifier
      .create(result)
      .expectError(CourseNotFoundException::class.java)
      .verify()

    verify(exactly = 1) { dao.findByStudentIdAndCourseId(any(), any()) }
    verify(exactly = 1) { studentService.findById(any()) }
    verify { courseService.findById(any()) wasNot called }
    verify { dao.save(any()) wasNot called }
  }

  @Test
  fun save_ok() {
    every { dao.findByStudentIdAndCourseId(any(), any()) } returns Mono.empty()
    every { studentService.findById(any()) } returns Mono.just(Student(1, "Rugal"))
    every { courseService.findById(any()) } returns Mono.just(Course(1, "Rugal"))
    every { dao.save(any()) } returns Mono.just(u)

    val result = this.service.save(NewRegistrationDto(u.studentId!!, u.courseId!!, u.score!!))

    StepVerifier
      .create(result)
      .expectNext(u)
      .verifyComplete()

    verify(exactly = 1) { dao.findByStudentIdAndCourseId(any(), any()) }
    verify(exactly = 1) { studentService.findById(any()) }
    verify(exactly = 1) { courseService.findById(any()) }
    verify(exactly = 1) { dao.save(any()) }
  }
}
