package ga.rugal.reactor.core.dao

import ga.rugal.reactor.core.entity.Tag
import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface TagDao : ReactiveCrudRepository<Tag, Int>
