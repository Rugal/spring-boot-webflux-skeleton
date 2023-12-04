package ga.rugal.reactor.springmvc.graphql

import ga.rugal.r2dbc.graphql.CourseDto
import ga.rugal.r2dbc.graphql.CourseResolver
import ga.rugal.r2dbc.graphql.RegistrationDto
import ga.rugal.r2dbc.graphql.UpdateCourseDto
import ga.rugal.reactor.core.service.CourseService
import ga.rugal.reactor.core.service.RegistrationService
import ga.rugal.reactor.springmvc.mapper.CourseMapper
import ga.rugal.reactor.springmvc.mapper.RegistrationMapper
import graphql.schema.DataFetchingEnvironment
import org.springframework.stereotype.Controller
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Controller
class CourseFieldResolver(
  private val courseService: CourseService,
  private val registrationService: RegistrationService,
) : CourseResolver {
  override fun registration(course: CourseDto, env: DataFetchingEnvironment): Flux<RegistrationDto> = this.registrationService
    .dao
    .findByCourseId(course.id)
    .map(RegistrationMapper.I::from)

  override fun delete(dto: CourseDto, env: DataFetchingEnvironment): Mono<Boolean> = this.courseService
    .deleteById(dto.id)

  override fun update(dto: CourseDto, input: UpdateCourseDto, env: DataFetchingEnvironment): Mono<CourseDto> =
    this.courseService.dao
      .findById(dto.id)
      .map { it.copy(name = input.name) }
      .flatMap { this.courseService.dao.save(it) }
      .map { CourseMapper.I.from(it) }
}
