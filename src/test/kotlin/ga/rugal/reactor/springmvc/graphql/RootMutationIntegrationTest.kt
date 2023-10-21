package ga.rugal.reactor.springmvc.graphql

import ga.rugal.IntegrationTestBase
import ga.rugal.reactor.core.entity.Course
import ga.rugal.reactor.core.entity.Registration
import ga.rugal.reactor.core.entity.Student
import ga.rugal.reactor.core.entity.Tag
import graphql.ErrorType
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureHttpGraphQlTester
import org.springframework.graphql.test.tester.GraphQlTester

@AutoConfigureHttpGraphQlTester
class RootMutationIntegrationTest(@Autowired val tester: GraphQlTester) : IntegrationTestBase() {

  private val u = Tag(
    id = 6,
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
    score = 100,
    studentId = 5,
    courseId = 3,
  )

  @Test
  fun createTag() {
    tester.documentName("createTag")
      .variable("input", mapOf("name" to u.name))
      .execute()
      .path("createTag.id").entity(Integer::class.java).isEqualTo(u.id)
      .path("createTag.name").entity(String::class.java).isEqualTo(u.name)
      .path("createTag.createAt").hasValue()
      .path("createTag.updateAt").hasValue()
  }

  @Test
  fun createRegistration_bad() {
    tester.documentName("createRegistration")
      .variable("input", mapOf("score" to r.score, "studentId" to 1, "courseId" to 1))
      .execute()
      .errors()
      .expect { it.errorType == ErrorType.ValidationError }
  }

  @Test
  fun createRegistration_good() {
    tester.documentName("createRegistration")
      .variable("input", mapOf("score" to r.score, "studentId" to r.studentId, "courseId" to r.courseId))
      .execute()
      .path("createRegistration.id").entity(Int::class.java).isEqualTo(18)
      .path("createRegistration.score").entity(Int::class.java).isEqualTo(r.score!!)
      .path("createRegistration.createAt").hasValue()
      .path("createRegistration.updateAt").hasValue()
  }

  @Test
  fun createCourse_good() {
    tester.documentName("createCourse")
      .variable("input", mapOf("name" to c.name))
      .execute()
      .path("createCourse.id").entity(Integer::class.java).isEqualTo(6)
      .path("createCourse.name").entity(String::class.java).isEqualTo(c.name)
      .path("createCourse.createAt").hasValue()
      .path("createCourse.updateAt").hasValue()
  }

  @Test
  fun createStudent_good() {
    tester.documentName("createStudent")
      .variable("input", mapOf("name" to s.name))
      .execute()
      .path("createStudent.id").entity(Integer::class.java).isEqualTo(6)
      .path("createStudent.name").entity(String::class.java).isEqualTo(s.name)
      .path("createStudent.createAt").hasValue()
      .path("createStudent.updateAt").hasValue()
  }
}
