package ga.rugal.reactor.springmvc.graphql

import ga.rugal.reactor.core.dao.TagDao
import ga.rugal.reactor.core.entity.Registration
import ga.rugal.reactor.core.entity.Tag
import ga.rugal.reactor.core.service.CourseService
import ga.rugal.reactor.core.service.RegistrationService
import ga.rugal.reactor.core.service.StudentService
import ga.rugal.reactor.core.service.TagService
import ga.rugal.reactor.springmvc.exception.RedundantRegistrationException
import com.ninjasquad.springmockk.MockkBean
import graphql.ErrorType
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest
import org.springframework.graphql.test.tester.GraphQlTester
import reactor.core.publisher.Mono

@ExtendWith(MockKExtension::class)
@GraphQlTest(RootMutation::class)
class RootMutationTest {

  @Autowired
  lateinit var tester: GraphQlTester

  @MockkBean
  lateinit var dao: TagDao

  @MockkBean
  lateinit var studentService: StudentService

  @MockkBean
  lateinit var courseService: CourseService

  @MockkBean
  lateinit var registrationService: RegistrationService

  @MockkBean
  lateinit var service: TagService

  private val u = Tag(
    id = 1,
    name = "Rugal",
  )

  private val r = Registration(
    id = 1,
    score = 100,
    studentId = 1,
    courseId = 1,
  )

  @BeforeEach
  fun setup() {
    every { service.tagDao } returns dao
    every { dao.save(any()) } returns Mono.just(u)
  }

  @Test
  fun createTag_good() {
    tester.documentName("createTag")
      .variable("input", mapOf("name" to u.name))
      .execute()
      .path("createTag.id").entity(Integer::class.java).isEqualTo(u.id)
      .path("createTag.name").entity(String::class.java).isEqualTo(u.name)
      .path("createTag.createAt").hasValue()
      .path("createTag.updateAt").hasValue()

    verify(exactly = 1) { dao.save(any()) }
  }

  @Test
  fun createRegistration_good() {
    every { registrationService.save(any()) } returns Mono.just(r)

    tester.documentName("createRegistration")
      .variable("input", mapOf("score" to r.score, "studentId" to r.studentId, "courseId" to r.courseId))
      .execute()
      .path("createRegistration.id").entity(Int::class.java).isEqualTo(r.id)
      .path("createRegistration.score").entity(Int::class.java).isEqualTo(r.score!!)
      .path("createRegistration.createAt").hasValue()
      .path("createRegistration.updateAt").hasValue()

    verify(exactly = 1) { registrationService.save(any()) }
  }

  @Test
  fun createRegistration_bad() {
    every { registrationService.save(any()) } returns Mono.error {
      RedundantRegistrationException(
        r.studentId!!,
        r.courseId!!
      )
    }

    tester.documentName("createRegistration")
      .variable("input", mapOf("score" to r.score, "studentId" to r.studentId, "courseId" to r.courseId))
      .execute()
      .errors()
      .expect { it.errorType == ErrorType.ValidationError }

    verify(exactly = 1) { registrationService.save(any()) }
  }
}
