package ga.rugal.reactor.core.service

import ga.rugal.IntegrationTestBase
import ga.rugal.reactor.core.entity.Registration
import ga.rugal.reactor.core.entity.Student
import ga.rugal.reactor.springmvc.exception.StudentReferenceException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import reactor.test.StepVerifier

class StudentServiceIntegrationTest(@Autowired private val service: StudentService) : IntegrationTestBase() {

  //<editor-fold defaultstate="collapsed" desc="setup">
  private val u = Registration(
    id = 1,
    score = 78,
    studentId = 5,
    courseId = 3,
  )

  private val s = Student(
    id = 0,
    name = "Adelheid",
  )

  @BeforeEach
  fun setup() {

  }
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="deleteById">
  @Test
  fun `delete ok`() {
    StepVerifier
      .create(this.service.dao.save(s).flatMap { service.deleteById(it.id!!) })
      .expectNextMatches { it }
      .verifyComplete()
  }

  @Test
  fun `delete error`() {
    StepVerifier
      .create(service.deleteById(1))
      .expectError(StudentReferenceException::class.java)
      .verify()
  }
  //</editor-fold>
}
