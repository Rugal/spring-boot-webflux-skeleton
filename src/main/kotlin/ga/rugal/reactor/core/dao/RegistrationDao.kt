package ga.rugal.reactor.core.dao

import ga.rugal.reactor.core.entity.Registration
import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface RegistrationDao: ReactiveCrudRepository<Registration, Int> {
}
