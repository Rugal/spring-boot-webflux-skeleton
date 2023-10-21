package ga.rugal.reactor.core.service

import ga.rugal.reactor.core.dao.StudentDao
import ga.rugal.reactor.core.entity.Student
import ga.rugal.reactor.springmvc.exception.StudentNotFoundException
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty

@Service
class StudentService(
  val dao: StudentDao
) {
  fun findById(id: Int): Mono<Student> = this.dao
    .findById(id)
    .switchIfEmpty { Mono.error { StudentNotFoundException(id) } }
}
