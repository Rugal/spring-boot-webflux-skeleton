package ga.rugal.reactor.springmvc.controller

import ga.rugal.reactor.core.entity.Tag
import ga.rugal.reactor.core.service.TagService
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
class TagController(
  @Autowired private val service: TagService
) {
  private val LOG = KotlinLogging.logger {}

  @GetMapping("/tag")
  fun get(): Mono<Tag> {
    LOG.info { "Rugal" }
    return this.service.tagDao.findById(1)
  }
}
