package ga.rugal.reactor.springmvc.graphql

import ga.rugal.IntegrationTestBase
import graphql.ErrorType
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureHttpGraphQlTester
import org.springframework.graphql.test.tester.GraphQlTester

@AutoConfigureHttpGraphQlTester
class RootQueryIntegrationTest(@Autowired val tester: GraphQlTester) : IntegrationTestBase() {

  //<editor-fold defaultstate="collapsed" desc="getTag">
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
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="getStudent">
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
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="findByPeerId 2">
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="getCourse">
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
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="getRegistration">
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
  //</editor-fold>

  @Test
  fun deleteRegistration_ok() {
    val id = 10
    tester.documentName("getRegistration")
      .variable("id", id)
      .executeAndVerify()

    tester.documentName("deleteRegistration")
      .variable("id", id)
      .executeAndVerify()

    tester.documentName("getRegistration")
      .variable("id", id)
      .execute()
      .errors()
      .expect { it.errorType == ErrorType.ValidationError }
  }

  @Test
  fun updateRegistration_ok() {
    val id = 1
    val score = 55
    tester.documentName("getRegistration")
      .variable("id", id)
      .execute()
      .path("getRegistration.id").entity(Int::class.java).isEqualTo(1)
      .path("getRegistration.score").entity(Int::class.java).isEqualTo(100)

    tester.documentName("updateRegistration")
      .variable("id", id)
      .variable("input", mapOf("score" to score))
      .execute()
      .path("getRegistration.update.id").entity(Int::class.java).isEqualTo(id)
      .path("getRegistration.update.score").entity(Int::class.java).isEqualTo(score)
  }
}
