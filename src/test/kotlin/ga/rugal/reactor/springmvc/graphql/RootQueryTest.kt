package ga.rugal.reactor.springmvc.graphql

import ga.rugal.reactor.core.entity.Course
import ga.rugal.reactor.core.entity.Registration
import ga.rugal.reactor.core.entity.Student
import ga.rugal.reactor.core.entity.Tag
import ga.rugal.reactor.core.service.CourseService
import ga.rugal.reactor.core.service.RegistrationService
import ga.rugal.reactor.core.service.StudentService
import ga.rugal.reactor.core.service.TagService
import ga.rugal.reactor.springmvc.exception.CourseNotFoundException
import ga.rugal.reactor.springmvc.exception.RegistrationNotFoundException
import ga.rugal.reactor.springmvc.exception.StudentNotFoundException
import ga.rugal.reactor.springmvc.exception.TagNotFoundException
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
@GraphQlTest(RootQuery::class, RegistrationFieldResolver::class)
class RootQueryTest {

  @Autowired
  lateinit var tester: GraphQlTester

  @MockkBean
  lateinit var service: TagService

  @MockkBean
  lateinit var courseService: CourseService

  @MockkBean
  lateinit var studentService: StudentService

  @MockkBean
  lateinit var registrationService: RegistrationService

  private val u = Tag(
    id = 1,
    name = "Rugal",
  )

  private val c = Course(
    id = 1,
    name = "Rugal",
  )

  private val s = Student(
    id = 1,
    name = "Rugal",
  )

  private val r = Registration(
    id = 1,
    score = 99,
    studentId = 1,
    courseId = 1,
  )

  @BeforeEach
  fun setup() {
    every { service.findById(any()) } returns Mono.just(u)
    every { courseService.findById(any()) } returns Mono.just(c)
    every { studentService.findById(any()) } returns Mono.just(s)
    every { registrationService.findById(any()) } returns Mono.just(r)
  }

  @Test
  fun getTag_other_exception() {
    every { service.findById(any()) } returns Mono.error { NullPointerException(s.name) }

    tester.documentName("getTag")
      .variable("id", 1)
      .execute()
      .errors()
      .expect { it.errorType == ErrorType.ValidationError }
      .expect { it.message == s.name }

    verify(exactly = 1) { service.findById(any()) }
  }

  @Test
  fun getTag_found() {
    tester.documentName("getTag")
      .variable("id", 1)
      .execute()
      .path("getTag.id").entity(Integer::class.java).isEqualTo(u.id)
      .path("getTag.name").entity(String::class.java).isEqualTo(u.name)

    verify(exactly = 1) { service.findById(any()) }
  }

  @Test
  fun getTag_not_found() {
    every { service.findById(any()) } returns Mono.error { TagNotFoundException(u.id) }

    tester.documentName("getTag")
      .variable("id", 1)
      .execute()
      .errors()
      .expect { it.errorType == ErrorType.ValidationError }

    verify(exactly = 1) { service.findById(any()) }
  }

  @Test
  fun getCourse_found() {
    tester.documentName("getCourse")
      .variable("id", 1)
      .executeAndVerify()

    verify(exactly = 1) { courseService.findById(any()) }
  }

  @Test
  fun getCourse_not_found() {
    every { courseService.findById(any()) } returns Mono.error { CourseNotFoundException(u.id) }

    tester.documentName("getCourse")
      .variable("id", 1)
      .execute()
      .errors()
      .expect { it.errorType == ErrorType.ValidationError }

    verify(exactly = 1) { courseService.findById(any()) }
  }

  @Test
  fun getStudent_found() {
    tester.documentName("getStudent")
      .variable("id", 1)
      .executeAndVerify()

    verify(exactly = 1) { studentService.findById(any()) }
  }

  @Test
  fun getStudent_not_found() {
    every { studentService.findById(any()) } returns Mono.error { StudentNotFoundException(u.id) }

    tester.documentName("getStudent")
      .variable("id", 1)
      .execute()
      .errors()
      .expect { it.errorType == ErrorType.ValidationError }

    verify(exactly = 1) { studentService.findById(any()) }
  }

  @Test
  fun getRegistration_found() {
    tester.documentName("getRegistration")
      .variable("id", 1)
      .execute()
      .path("getRegistration.score").entity(Int::class.java).isEqualTo(r.score!!)
      .path("getRegistration.student.id").entity(Int::class.java).isEqualTo(s.id)
      .path("getRegistration.course.id").entity(Int::class.java).isEqualTo(c.id)

    verify(exactly = 3) { registrationService.findById(any()) }
    verify(exactly = 1) { studentService.findById(any()) }
    verify(exactly = 1) { courseService.findById(any()) }
  }

  @Test
  fun getRegistration_not_found() {
    every { registrationService.findById(any()) } returns Mono.error { RegistrationNotFoundException(u.id) }

    tester.documentName("getRegistration")
      .variable("id", 1)
      .execute()
      .errors()
      .expect { it.errorType == ErrorType.ValidationError }

    verify(exactly = 1) { registrationService.findById(any()) }
  }
}
