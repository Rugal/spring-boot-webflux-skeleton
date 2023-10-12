package ga.rugal.reactor.springmvc.controller

import ga.rugal.reactor.core.dao.TagDao
import ga.rugal.reactor.core.entity.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
class TagController(
  @Autowired val dao: TagDao
) {
  @GetMapping("/tag")
  fun get(): Mono<Tag> = this.dao.findById(1)
}
