package ga.rugal.reactor.springmvc.graphql

import ga.rugal.reactor.core.dao.CourseDao
import ga.rugal.reactor.core.dao.RegistrationDao
import ga.rugal.reactor.core.dao.StudentDao
import ga.rugal.reactor.core.entity.Course
import ga.rugal.reactor.core.entity.Registration
import ga.rugal.reactor.core.entity.Student
import ga.rugal.reactor.core.entity.Tag
import ga.rugal.reactor.core.service.CourseService
import ga.rugal.reactor.core.service.RegistrationService
import ga.rugal.reactor.core.service.StudentService
import ga.rugal.reactor.core.service.TagService
import ga.rugal.reactor.springmvc.exception.CourseNotFoundException
import ga.rugal.reactor.springmvc.exception.CourseReferenceException
import ga.rugal.reactor.springmvc.exception.RegistrationNotFoundException
import ga.rugal.reactor.springmvc.exception.StudentNotFoundException
import ga.rugal.reactor.springmvc.exception.StudentReferenceException
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
@GraphQlTest(RootQuery::class, RegistrationFieldResolver::class, StudentFieldResolver::class, CourseFieldResolver::class)
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

  @MockkBean
  lateinit var dao: RegistrationDao

  @MockkBean
  lateinit var studentDao: StudentDao

  @MockkBean
  lateinit var courseDao: CourseDao

  private val t = Tag(
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
    every { service.findById(any()) } returns Mono.just(t)
    every { courseService.findById(any()) } returns Mono.just(c)
    every { studentService.findById(any()) } returns Mono.just(s)
    every { registrationService.findById(any()) } returns Mono.just(r)

    every { registrationService.dao } returns this.dao
    every { studentService.dao } returns this.studentDao
    every { courseService.dao } returns this.courseDao
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
      .path("getTag.id").entity(Integer::class.java).isEqualTo(t.id)
      .path("getTag.name").entity(String::class.java).isEqualTo(t.name)

    verify(exactly = 1) { service.findById(any()) }
  }

  @Test
  fun getTag_not_found() {
    every { service.findById(any()) } returns Mono.error { TagNotFoundException(t.id) }

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
    every { courseService.findById(any()) } returns Mono.error { CourseNotFoundException(t.id) }

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
    every { studentService.findById(any()) } returns Mono.error { StudentNotFoundException(t.id) }

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
    every { registrationService.findById(any()) } returns Mono.error { RegistrationNotFoundException(t.id) }

    tester.documentName("getRegistration")
      .variable("id", 1)
      .execute()
      .errors()
      .expect { it.errorType == ErrorType.ValidationError }

    verify(exactly = 1) { registrationService.findById(any()) }
  }

  @Test
  fun deleteRegistration_ok() {
    every { dao.deleteById(1) } returns Mono.empty()

    tester.documentName("deleteRegistration")
      .variable("id", 1)
      .execute()
      .path("getRegistration.delete").entity(Boolean::class.java).isEqualTo(true)

    verify(exactly = 1) { registrationService.findById(any()) }
    verify(exactly = 1) { dao.deleteById(1) }
  }

  @Test
  fun updateRegistration_ok() {
    every { dao.findById(r.id) } returns Mono.just(r)
    every { dao.save(any()) } returns Mono.just(r)

    tester.documentName("updateRegistration")
      .variable("id", r.id)
      .variable("input", mapOf("score" to r.score))
      .execute()
      .path("getRegistration.update.id").entity(Int::class.java).isEqualTo(r.id)
      .path("getRegistration.update.score").entity(Int::class.java).isEqualTo(r.score!!)

    verify(exactly = 1) { dao.findById(r.id) }
    verify(exactly = 1) { dao.save(any()) }
  }

  @Test
  fun deleteStudent_ok() {
    every { studentService.deleteById(any()) } returns Mono.just(true)

    tester.documentName("deleteStudent")
      .variable("id", 1)
      .execute()
      .path("getStudent.delete").entity(Boolean::class.java).isEqualTo(true)

    verify(exactly = 1) { studentService.deleteById(any()) }
  }

  @Test
  fun deleteStudent_error() {
    every { studentService.deleteById(any()) } returns Mono.error(StudentReferenceException(1))

    tester.documentName("deleteStudent")
      .variable("id", 1)
      .execute()
      .errors()
      .expect { it.errorType == ErrorType.ValidationError }

    verify(exactly = 1) { studentService.deleteById(any()) }
  }

  @Test
  fun updateStudent_ok() {
    every { studentDao.findById(r.id) } returns Mono.just(s)
    every { studentDao.save(any()) } returns Mono.just(s)

    tester.documentName("updateStudent")
      .variable("id", r.id)
      .variable("input", mapOf("name" to s.name))
      .execute()
      .path("getStudent.update.id").entity(Int::class.java).isEqualTo(r.id)
      .path("getStudent.update.name").entity(String::class.java).isEqualTo(s.name)

    verify(exactly = 1) { studentDao.findById(s.id) }
    verify(exactly = 1) { studentDao.save(any()) }
  }

  @Test
  fun deleteCourse_ok() {
    every { courseService.deleteById(any()) } returns Mono.just(true)

    tester.documentName("deleteCourse")
      .variable("id", 1)
      .execute()
      .path("getCourse.delete").entity(Boolean::class.java).isEqualTo(true)

    verify(exactly = 1) { courseService.deleteById(any()) }
  }

  @Test
  fun deleteCourse_error() {
    every { courseService.deleteById(any()) } returns Mono.error(CourseReferenceException(1))

    tester.documentName("deleteCourse")
      .variable("id", 1)
      .execute()
      .errors()
      .expect { it.errorType == ErrorType.ValidationError }

    verify(exactly = 1) { courseService.deleteById(any()) }
  }

  @Test
  fun updateCourse_ok() {
    every { courseDao.findById(r.id) } returns Mono.just(c)
    every { courseDao.save(any()) } returns Mono.just(c)

    tester.documentName("updateCourse")
      .variable("id", r.id)
      .variable("input", mapOf("name" to s.name))
      .execute()
      .path("getCourse.update.id").entity(Int::class.java).isEqualTo(r.id)
      .path("getCourse.update.name").entity(String::class.java).isEqualTo(s.name)

    verify(exactly = 1) { courseDao.findById(s.id) }
    verify(exactly = 1) { courseDao.save(any()) }
  }
}
