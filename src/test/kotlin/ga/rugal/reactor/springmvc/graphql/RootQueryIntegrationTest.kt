package ga.rugal.reactor.springmvc.graphql

import ga.rugal.IntegrationTestBase
import graphql.ErrorType
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureHttpGraphQlTester
import org.springframework.graphql.test.tester.GraphQlTester

@AutoConfigureHttpGraphQlTester
class RootQueryIntegrationTest(@Autowired val tester: GraphQlTester) : IntegrationTestBase() {

  @Test
  fun getTag_found() {
    tester.documentName("getTag")
      .variable("id", 1)
      .executeAndVerify()
  }

  @Test
  fun getTag_not_found() {
    tester.documentName("getTag")
      .variable("id", 0)
      .execute()
      .errors()
      .expect { it.errorType == ErrorType.ValidationError }
  }
}
