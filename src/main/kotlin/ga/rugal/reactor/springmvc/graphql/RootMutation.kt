package ga.rugal.reactor.springmvc.graphql

import ga.rugal.r2dbc.graphql.MutationResolver
import ga.rugal.r2dbc.graphql.NewTagDto
import ga.rugal.r2dbc.graphql.TagDto
import ga.rugal.reactor.core.service.TagService
import ga.rugal.reactor.springmvc.mapper.TagMapper
import graphql.schema.DataFetchingEnvironment
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Controller
import reactor.core.publisher.Mono

/**
 * GraphQL root mutation.
 *
 * @author Rugal Bernstein
 */
@Controller
class RootMutation(private val userService: TagService) : MutationResolver {
  private val LOG = KotlinLogging.logger {}

  override fun createTag(input: NewTagDto, env: DataFetchingEnvironment): Mono<TagDto> = Mono.just(input)
    .map { TagMapper.I.to(it) }
    .flatMap { userService.tagDao.save(it) }
    .map { TagMapper.I.from(it) }
}
