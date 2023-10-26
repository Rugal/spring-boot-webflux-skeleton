package ga.rugal.reactor.springmvc.graphql

import ga.rugal.r2dbc.graphql.CourseDto
import ga.rugal.r2dbc.graphql.CourseResolver
import ga.rugal.r2dbc.graphql.UpdateCourseDto
import ga.rugal.reactor.core.service.CourseService
import ga.rugal.reactor.springmvc.mapper.CourseMapper
import graphql.schema.DataFetchingEnvironment
import org.springframework.stereotype.Controller
import reactor.core.publisher.Mono

@Controller
class CourseFieldResolver(private val courseService: CourseService) : CourseResolver {

  override fun delete(dto: CourseDto, env: DataFetchingEnvironment): Mono<Boolean> = this.courseService.dao
  .deleteById(dto.id)
  .thenReturn(true)

  override fun update(dto: CourseDto, input: UpdateCourseDto, env: DataFetchingEnvironment): Mono<CourseDto> = this.courseService.dao
    .findById(dto.id)
    .map { it.copy(name = input.name) }
    .flatMap { this.courseService.dao.save(it) }
    .map { CourseMapper.I.from(it) }
}
