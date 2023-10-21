package ga.rugal.reactor.springmvc.controller

import ga.rugal.reactor.core.entity.Tag
import ga.rugal.reactor.core.service.TagService
import ga.rugal.reactor.springmvc.exception.TagNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
class TagController(
  @Autowired private val service: TagService
) {
  @GetMapping("/tag/{id}")
  fun get(@PathVariable id: Int): Mono<ResponseEntity<Tag>> = this.service
    .findById(id)
    .map { ResponseEntity.ok(it) }
    .onErrorResume(TagNotFoundException::class.java) { Mono.just(ResponseEntity.notFound().build()) }
}
