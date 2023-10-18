package ga.rugal.reactor.springmvc.graphql

import ga.rugal.IntegrationTestBase
import ga.rugal.reactor.core.entity.Tag
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
}
