package ga.rugal.reactor.core.service

import ga.rugal.reactor.core.dao.TagDao
import ga.rugal.reactor.core.entity.Tag
import ga.rugal.reactor.springmvc.exception.TagNotFoundException
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty

@Service
class TagService(
  val dao: TagDao
) {
  fun findById(id: Int): Mono<Tag> = this.dao
    .findById(id)
    .switchIfEmpty { Mono.error { TagNotFoundException(id) } }

  /**
   * Delete entry if found, otherwise emit error.
   * Return iff entry exist.
   */
  @Throws(TagNotFoundException::class)
  fun deleteById(id: Int): Mono<Boolean> = this.findById(id)
    .doOnNext { this.dao.deleteById(id).subscribe() }
    .hasElement()
}
