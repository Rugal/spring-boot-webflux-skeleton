package ga.rugal.reactor.core.service

import ga.rugal.reactor.core.dao.CourseDao
import ga.rugal.reactor.core.entity.Course
import ga.rugal.reactor.springmvc.exception.CourseNotFoundException
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty

@Service
class CourseService(
  val dao: CourseDao
) {
  private val LOG = KotlinLogging.logger {}

  fun findById(id: Int): Mono<Course> = this.dao
    .findById(id)
    .switchIfEmpty { Mono.error { CourseNotFoundException(id) } }
}
