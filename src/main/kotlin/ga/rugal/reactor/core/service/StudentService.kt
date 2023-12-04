package ga.rugal.reactor.core.service

import ga.rugal.reactor.core.dao.RegistrationDao
import ga.rugal.reactor.core.dao.StudentDao
import ga.rugal.reactor.core.entity.Student
import ga.rugal.reactor.springmvc.exception.StudentNotFoundException
import ga.rugal.reactor.springmvc.exception.StudentReferenceException
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty

@Service
class StudentService(
  val dao: StudentDao,
  val registrationDao: RegistrationDao,
) {

  fun findById(id: Int): Mono<Student> = this.dao
    .findById(id)
    .switchIfEmpty { Mono.error { StudentNotFoundException(id) } }

  /**
   * check any existing registration is under this entity before deleting it.
   */
  fun deleteById(id: Int): Mono<Boolean> = this.registrationDao
    .findByStudentId(id)
    .hasElements()
    // unable to delete if true
    .filter { it == false }
    .doOnNext { this.dao.deleteById(id).subscribe() }
    .switchIfEmpty { Mono.error(StudentReferenceException(id)) }
    .map { !it }
}
