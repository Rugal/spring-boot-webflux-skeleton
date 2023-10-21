package ga.rugal.reactor.core.service

import ga.rugal.reactor.core.dao.TagDao
import ga.rugal.reactor.core.entity.Tag
import ga.rugal.reactor.springmvc.exception.TagNotFoundException
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty

@Service
class TagService(
  val tagDao: TagDao
) {
  fun findById(id: Int): Mono<Tag> = this.tagDao
    .findById(id)
    .switchIfEmpty { Mono.error { TagNotFoundException(id) } }
}
