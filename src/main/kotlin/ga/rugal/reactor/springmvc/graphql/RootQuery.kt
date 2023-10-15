package ga.rugal.reactor.springmvc.graphql

import ga.rugal.r2dbc.graphql.QueryResolver
import ga.rugal.r2dbc.graphql.TagDto
import ga.rugal.reactor.core.service.TagService
import graphql.schema.DataFetchingEnvironment
import reactor.core.publisher.Mono

class RootQuery(
  private val tagService: TagService
) : QueryResolver {
  override fun getTag(id: Int, env: DataFetchingEnvironment): Mono<TagDto> {
//    return this.tagService.tagDao.findById(id)
    return Mono.just(TagDto())
  }
}
