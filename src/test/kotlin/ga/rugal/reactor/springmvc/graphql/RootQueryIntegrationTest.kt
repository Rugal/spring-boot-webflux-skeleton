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

  @Test
  fun getStudent_found() {
    tester.documentName("getStudent")
      .variable("id", 1)
      .executeAndVerify()
  }

  @Test
  fun getStudent_not_found() {
    tester.documentName("getStudent")
      .variable("id", 0)
      .execute()
      .errors()
      .expect { it.errorType == ErrorType.ValidationError }
  }

  @Test
  fun getCourse_found() {
    tester.documentName("getCourse")
      .variable("id", 1)
      .executeAndVerify()
  }

  @Test
  fun getCourse_not_found() {
    tester.documentName("getCourse")
      .variable("id", 0)
      .execute()
      .errors()
      .expect { it.errorType == ErrorType.ValidationError }
  }

  @Test
  fun getRegistration_found() {
    tester.documentName("getRegistration")
      .variable("id", 1)
      .executeAndVerify()
  }

  @Test
  fun getRegistration_not_found() {
    tester.documentName("getRegistration")
      .variable("id", 0)
      .execute()
      .errors()
      .expect { it.errorType == ErrorType.ValidationError }
  }
}
