package ga.rugal.reactor.core.service

import ga.rugal.reactor.core.dao.TagDao
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service

@Service
class TagService(
  val tagDao: TagDao
) {
  private val LOG = KotlinLogging.logger {}

}
