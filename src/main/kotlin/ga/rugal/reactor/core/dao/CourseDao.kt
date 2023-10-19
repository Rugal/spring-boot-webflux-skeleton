package ga.rugal.reactor.core.dao

import ga.rugal.reactor.core.entity.Course
import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface CourseDao: ReactiveCrudRepository<Course, Int>
