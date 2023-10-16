package ga.rugal.reactor.springmvc.graphql

import ga.rugal.reactor.core.entity.Tag
import ga.rugal.reactor.core.service.TagService
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
@GraphQlTest(RootQuery::class)
class RootQueryTest {

  @Autowired
  lateinit var tester: GraphQlTester

  @MockkBean
  lateinit var service: TagService

  private val u = Tag(
    id = 1,
    name = "Rugal",
  )

  @BeforeEach
  fun setup() {
    every { service.findById(any()) } returns Mono.just(u)
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
}
