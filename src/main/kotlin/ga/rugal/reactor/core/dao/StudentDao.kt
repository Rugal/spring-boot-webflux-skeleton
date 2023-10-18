package ga.rugal.reactor.core.dao

import ga.rugal.reactor.core.entity.Student
import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface StudentDao: ReactiveCrudRepository<Student, Int> {
}
