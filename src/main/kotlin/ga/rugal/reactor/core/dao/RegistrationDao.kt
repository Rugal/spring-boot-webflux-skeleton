package ga.rugal.reactor.core.dao

import ga.rugal.reactor.core.entity.Registration
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface RegistrationDao : ReactiveCrudRepository<Registration, Int> {

  fun findByStudentId(id: Int): Flux<Registration>

  fun findByCourseId(id: Int): Flux<Registration>

  fun findByStudentIdAndCourseId(studentId: Int, courseId: Int): Mono<Registration>

  fun existsByCourseId(id: Int): Mono<Boolean>

  fun existsByStudentId(id: Int): Mono<Boolean>
}
