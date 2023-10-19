package ga.rugal.reactor.core.dao

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import reactor.test.StepVerifier

class RegistrationDaoIntegrationTest(@Autowired val dao: RegistrationDao) : DaoIntegrationTestBase() {

  @Test
  fun findAll_ok() {
    StepVerifier
      .create(this.dao.findAll())
      .expectNextCount(17)
      .verifyComplete()
  }

  @Test
  fun findById_ok() {
    StepVerifier
      .create(this.dao.findById(1))
      .expectNextMatches { it.studentId == 1 && it.courseId == 1 && it.score == 100 }
      .verifyComplete()
  }

  @Test
  fun findByStudentId() {
    StepVerifier
      .create(this.dao.findByStudentId(1))
      .expectNextCount(3)
      .verifyComplete()
  }

  @Test
  fun findByCourseId() {
    StepVerifier
      .create(this.dao.findByCourseId(1))
      .expectNextCount(4)
      .verifyComplete()
  }

  @Test
  fun findByStudentIdAndCourseId() {
    StepVerifier
      .create(this.dao.findByStudentIdAndCourseId(1, 1))
      .expectNextMatches { it.id == 1 && it.score == 100 }
      .verifyComplete()
  }
}
