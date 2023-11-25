package ga.rugal.reactor.core.service

import ga.rugal.r2dbc.graphql.NewRegistrationDto
import ga.rugal.reactor.core.dao.RegistrationDao
import ga.rugal.reactor.core.entity.Registration
import ga.rugal.reactor.springmvc.exception.RedundantRegistrationException
import ga.rugal.reactor.springmvc.exception.RegistrationNotFoundException
import ga.rugal.reactor.springmvc.mapper.RegistrationMapper
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty

@Service
class RegistrationService(
  val dao: RegistrationDao,
  private val studentService: StudentService,
  private val courseService: CourseService,
) {
  fun findById(id: Int): Mono<Registration> = this.dao
    .findById(id)
    .switchIfEmpty { Mono.error { RegistrationNotFoundException(id) } }

  fun save(input: NewRegistrationDto): Mono<Registration> = Mono.just(input)
    .flatMap { this.dao.findByStudentIdAndCourseId(it.studentId, it.courseId).hasElement() }
    // ensure the student & course pair is unique
    .flatMap {
      if (it) Mono.error(RedundantRegistrationException(input.studentId, input.courseId)) else Mono.just(input)
    }
    .flatMap { studentService.findById(input.studentId) }  // this would emit error
    .flatMap { courseService.findById(input.courseId) }    // this would emit error as well
    .flatMap { Mono.just(input) } // definitely not elegant
    .map { RegistrationMapper.I.to(it) }
    .flatMap { dao.save(it) }

  fun deleteById(id: Int): Mono<Boolean> = this.dao.findById(id)
    .doOnNext { this.dao.deleteById(id) }
    .hasElement()
}
