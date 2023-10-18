package ga.rugal.reactor.springmvc.graphql

import ga.rugal.r2dbc.graphql.CourseDto
import ga.rugal.r2dbc.graphql.RegistrationDto
import ga.rugal.r2dbc.graphql.RegistrationResolver
import ga.rugal.r2dbc.graphql.StudentDto
import ga.rugal.reactor.core.service.RegistrationService
import ga.rugal.reactor.springmvc.mapper.CourseMapper
import ga.rugal.reactor.springmvc.mapper.StudentMapper
import graphql.schema.DataFetchingEnvironment
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class RegistrationFieldResolver(
  private val registrationService: RegistrationService,
) : RegistrationResolver {


  override fun student(input: RegistrationDto, env: DataFetchingEnvironment): Mono<StudentDto> {
    return registrationService
      .findById(input.id)
      .map { StudentMapper.I.from(it.student!!) }
  }

  override fun course(input: RegistrationDto, env: DataFetchingEnvironment): Mono<CourseDto> {
    return registrationService
      .findById(input.id)
      .map { CourseMapper.I.from(it.course!!) }
  }
}
