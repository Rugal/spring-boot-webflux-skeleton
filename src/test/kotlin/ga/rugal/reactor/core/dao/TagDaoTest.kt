package ga.rugal.reactor.core.dao

import config.Main
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import reactor.test.StepVerifier

@SpringBootTest(classes = [Main::class])
@ExtendWith(SpringExtension::class)
class TagDaoTest(@Autowired val dao: TagDao) {

  @Test
  fun findAll_ok() {
    StepVerifier
      .create(this.dao.findAll())
      .expectNextCount(15)
      .verifyComplete()
  }

  @Test
  fun findById_ok() {
    StepVerifier
      .create(this.dao.findById(1))
      .assertNext { Assertions.assertEquals("BlueRay", it.name) }
      .verifyComplete()
  }
}
