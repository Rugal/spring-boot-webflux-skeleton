package ga.rugal.reactor.springmvc.graphql

import ga.rugal.r2dbc.graphql.CourseDto
import ga.rugal.r2dbc.graphql.NewRegistrationDto
import ga.rugal.r2dbc.graphql.RegistrationDto
import ga.rugal.r2dbc.graphql.RegistrationResolver
import ga.rugal.r2dbc.graphql.StudentDto
import ga.rugal.reactor.core.service.CourseService
import ga.rugal.reactor.core.service.RegistrationService
import ga.rugal.reactor.core.service.StudentService
import ga.rugal.reactor.springmvc.mapper.CourseMapper
import ga.rugal.reactor.springmvc.mapper.StudentMapper
import graphql.schema.DataFetchingEnvironment
import org.springframework.stereotype.Controller
import reactor.core.publisher.Mono

@Controller
class RegistrationFieldResolver(
  private val studentService: StudentService,
  private val courseService: CourseService,
  private val registrationService: RegistrationService,
) : RegistrationResolver {

  override fun student(input: RegistrationDto, env: DataFetchingEnvironment): Mono<StudentDto> = registrationService
    .findById(input.id)
    .flatMap { studentService.findById(it.studentId!!) }
    .map { StudentMapper.I.from(it) }

  override fun course(input: RegistrationDto, env: DataFetchingEnvironment): Mono<CourseDto> = registrationService
    .findById(input.id)
    .flatMap { courseService.findById(it.studentId!!) }
    .map { CourseMapper.I.from(it) }

  override fun update(
    registration: RegistrationDto?,
    input: NewRegistrationDto?,
    env: DataFetchingEnvironment?
  ): Mono<RegistrationDto> {
    TODO("Not yet implemented")
  }

  override fun delete(dto: RegistrationDto, env: DataFetchingEnvironment): Mono<Boolean> = this.registrationService.dao
    .deleteById(dto.id)
    .thenReturn(true)
}
