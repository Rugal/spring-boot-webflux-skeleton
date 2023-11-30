package ga.rugal.reactor.core.service

import ga.rugal.r2dbc.graphql.NewRegistrationDto
import ga.rugal.reactor.core.dao.RegistrationDao
import ga.rugal.reactor.core.entity.Registration
import ga.rugal.reactor.springmvc.exception.RedundantRegistrationException
import ga.rugal.reactor.springmvc.exception.RegistrationNotFoundException
import ga.rugal.reactor.springmvc.mapper.RegistrationMapper
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty

@Service
open class RegistrationService(
  val dao: RegistrationDao,
  private val studentService: StudentService,
  private val courseService: CourseService,
) {
  fun findById(id: Int): Mono<Registration> = this.dao
    .findById(id)
    .switchIfEmpty { Mono.error { RegistrationNotFoundException(id) } }

  /**
   * 1. there is no existing registration
   * 2. course exist
   * 3. student exist
   * 4. save it
   * 5. return persisted registration
   */
  fun save(input: NewRegistrationDto): Mono<Registration> {
    fun notExistsByStudentAndCourse(studentId: Int, courseId: Int): Mono<Boolean> =
      this.dao.findByStudentIdAndCourseId(studentId, courseId).hasElement().map { !it }

    return Flux
      .merge(
        notExistsByStudentAndCourse(input.studentId, input.courseId), // return true/false
        studentService.findById(input.studentId).hasElement(), // will emit error if wrong
        courseService.findById(input.courseId).hasElement(),   // will emit error if wrong
      )
      .reduce { t, u -> t && u }
      .filter { it } // either true or some error
      .switchIfEmpty { Mono.error(RedundantRegistrationException(input.studentId, input.courseId)) }
      .map { RegistrationMapper.I.to(input) }
      .flatMap { dao.save(it) }
  }

  /**
   * Delete entry if found, otherwise emit error.
   * Return iff entry exist.
   */
  @Throws(RegistrationNotFoundException::class)
  fun deleteById(id: Int): Mono<Boolean> = this.findById(id)
    .doOnNext { this.dao.deleteById(id).subscribe() }
    .hasElement()
}
