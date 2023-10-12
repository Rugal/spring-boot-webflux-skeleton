package ga.rugal.reactor.core.service

import ga.rugal.reactor.core.dao.TagDao
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TagService(
  val tagDao: TagDao
) {

}
