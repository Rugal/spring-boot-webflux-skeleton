package ga.rugal.reactor.springmvc.graphql

import ga.rugal.reactor.core.dao.TagDao
import ga.rugal.reactor.core.entity.Tag
import ga.rugal.reactor.core.service.TagService
import com.ninjasquad.springmockk.MockkBean
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
  lateinit var service: TagService

  private val u = Tag(
    id = 1,
    name = "Rugal",
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

    verify(exactly = 1) { dao.save(any()) }
  }
}
