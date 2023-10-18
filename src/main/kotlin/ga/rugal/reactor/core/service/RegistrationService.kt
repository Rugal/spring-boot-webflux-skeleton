package ga.rugal.reactor.core.service

import ga.rugal.reactor.core.dao.RegistrationDao
import ga.rugal.reactor.core.entity.Registration
import ga.rugal.reactor.springmvc.exception.RegistrationNotFoundException
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty

@Service
class RegistrationService(
  val dao: RegistrationDao
) {
  private val LOG = KotlinLogging.logger {}

  fun findById(id: Int): Mono<Registration> = this.dao
    .findById(id)
    .switchIfEmpty { Mono.error { RegistrationNotFoundException(id) } }
}
