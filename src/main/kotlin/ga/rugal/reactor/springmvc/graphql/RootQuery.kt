package ga.rugal.reactor.springmvc.graphql

import ga.rugal.r2dbc.graphql.QueryResolver
import ga.rugal.r2dbc.graphql.TagDto
import ga.rugal.reactor.core.service.TagService
import ga.rugal.reactor.springmvc.mapper.TagMapper
import graphql.schema.DataFetchingEnvironment
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Controller
import reactor.core.publisher.Mono

@Controller
class RootQuery(
  private val tagService: TagService
) : QueryResolver {
  private val LOG = KotlinLogging.logger {}

  override fun getTag(id: Int, env: DataFetchingEnvironment): Mono<TagDto> = this.tagService
    .findById(id)
    .map { TagMapper.I.from(it) }
}
