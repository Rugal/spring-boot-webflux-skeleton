package ga.rugal.reactor.core.service

import ga.rugal.reactor.core.dao.CourseDao
import ga.rugal.reactor.core.entity.Course
import ga.rugal.reactor.springmvc.exception.CourseNotFoundException
import ga.rugal.reactor.springmvc.exception.CourseReferenceException
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty

@Service
class CourseService(
  val dao: CourseDao,
  @Lazy private val registrationService: RegistrationService,
) {
  fun findById(id: Int): Mono<Course> = this.dao
    .findById(id)
    .switchIfEmpty { Mono.error { CourseNotFoundException(id) } }

  /**
   * check any existing registration is under this entity before deleting it.
   */
  fun deleteById(id: Int): Mono<Boolean> = this.registrationService.dao
    .existsByCourseId(id)
    // unable to delete if true
    .filter { it == false }
    .switchIfEmpty { Mono.error(CourseReferenceException(id)) }
    .map { this.dao.deleteById(id) }
    .thenReturn(true)
}
