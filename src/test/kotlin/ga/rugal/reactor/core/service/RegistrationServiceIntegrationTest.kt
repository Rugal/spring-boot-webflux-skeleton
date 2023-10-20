package ga.rugal.reactor.core.service

import ga.rugal.IntegrationTestBase
import ga.rugal.r2dbc.graphql.NewRegistrationDto
import ga.rugal.reactor.core.entity.Registration
import ga.rugal.reactor.springmvc.exception.CourseNotFoundException
import ga.rugal.reactor.springmvc.exception.RedundantRegistrationException
import ga.rugal.reactor.springmvc.exception.StudentNotFoundException
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import reactor.test.StepVerifier

class RegistrationServiceIntegrationTest(@Autowired private val service: RegistrationService) : IntegrationTestBase() {

  private val u = Registration(
    id = 1,
    score = 78,
    studentId = 5,
    courseId = 3,
  )

  @Test
  fun save_redundant() {
    val result = this.service.save(NewRegistrationDto(1, 1, u.score!!))

    StepVerifier
      .create(result)
      .expectError(RedundantRegistrationException::class.java)
      .verify()
  }

  @Test
  fun save_student_not_found() {
    val result = this.service.save(NewRegistrationDto(0, u.courseId!!, u.score!!))

    StepVerifier
      .create(result)
      .expectError(StudentNotFoundException::class.java)
      .verify()
  }

  @Test
  fun save_course_not_found() {
    val result = this.service.save(NewRegistrationDto(u.studentId!!, 0, u.score!!))

    StepVerifier
      .create(result)
      .expectError(CourseNotFoundException::class.java)
      .verify()
  }

  @Test
  fun save_ok() {
    val result = this.service.save(NewRegistrationDto(u.studentId!!, u.courseId!!, u.score!!))

    StepVerifier
      .create(result)
      .expectNextMatches { it.score == u.score && it.studentId == u.studentId && it.courseId == u.courseId }
      .verifyComplete()
  }
}
