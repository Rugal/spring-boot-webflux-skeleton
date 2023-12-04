package ga.rugal.reactor.springmvc.graphql

import ga.rugal.r2dbc.graphql.CourseDto
import ga.rugal.r2dbc.graphql.RegistrationDto
import ga.rugal.r2dbc.graphql.RegistrationResolver
import ga.rugal.r2dbc.graphql.StudentDto
import ga.rugal.r2dbc.graphql.UpdateRegistrationDto
import ga.rugal.reactor.core.service.CourseService
import ga.rugal.reactor.core.service.RegistrationService
import ga.rugal.reactor.core.service.StudentService
import ga.rugal.reactor.springmvc.mapper.CourseMapper
import ga.rugal.reactor.springmvc.mapper.RegistrationMapper
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
    .flatMap { studentService.findById(it.studentId) }
    .map(StudentMapper.I::from)

  override fun course(input: RegistrationDto, env: DataFetchingEnvironment): Mono<CourseDto> = registrationService
    .findById(input.id)
    .flatMap { courseService.findById(it.courseId) }
    .map(CourseMapper.I::from)

  override fun update(
    dto: RegistrationDto,
    input: UpdateRegistrationDto,
    env: DataFetchingEnvironment
  ): Mono<RegistrationDto> = this.registrationService.dao
    .findById(dto.id)
    .map { it.copy(score = input.score) }
    .flatMap { this.registrationService.dao.save(it) }
    .map { RegistrationMapper.I.from(it) }

  override fun delete(dto: RegistrationDto, env: DataFetchingEnvironment): Mono<Boolean> =
    this.registrationService.deleteById(dto.id)
}
