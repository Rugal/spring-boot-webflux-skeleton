package ga.rugal.reactor.core.service

import ga.rugal.UnitTestBase
import ga.rugal.reactor.core.dao.TagDao
import ga.rugal.reactor.core.entity.Tag
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

class TagServiceTest : UnitTestBase() {

  @MockK
  lateinit var dao: TagDao

  @InjectMockKs
  lateinit var service: TagService

  @Test
  fun test_one() {
    every { dao.findById(1) } returns Mono.just(Tag(1, "rugal"))

    val result = this.service.tagDao.findById(1)

    StepVerifier
      .create(result)
      .assertNext { Assertions.assertEquals(1, it.id) }
      .verifyComplete()

    verify(exactly = 1) { dao.findById(1) }
  }

  @Test
  fun test_empty() {
    every { dao.findById(1) } returns Mono.empty()

    val result = this.service.tagDao.findById(1)

    StepVerifier
      .create(result)
      .verifyComplete()

    verify(exactly = 1) { dao.findById(1) }
  }
}
