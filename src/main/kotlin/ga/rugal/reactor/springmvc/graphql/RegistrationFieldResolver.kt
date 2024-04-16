package ga.rugal.reactor.springmvc.graphql

import ga.rugal.r2dbc.graphql.CourseDto
import ga.rugal.r2dbc.graphql.RegistrationDto
import ga.rugal.r2dbc.graphql.RegistrationResolver
import ga.rugal.r2dbc.graphql.StudentDto
import ga.rugal.r2dbc.graphql.UpdateRegistrationDto
import ga.rugal.reactor.core.entity.Registration
import ga.rugal.reactor.core.entity.Student
import ga.rugal.reactor.core.service.CourseService
import ga.rugal.reactor.core.service.RegistrationService
import ga.rugal.reactor.core.service.StudentService
import ga.rugal.reactor.springmvc.mapper.CourseMapper
import ga.rugal.reactor.springmvc.mapper.RegistrationMapper
import ga.rugal.reactor.springmvc.mapper.StudentMapper
import graphql.schema.DataFetchingEnvironment
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.graphql.data.method.annotation.BatchMapping
import org.springframework.stereotype.Controller
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.collectMap
import reactor.kotlin.core.publisher.toFlux

@Controller
class RegistrationFieldResolver(
  private val studentService: StudentService,
  private val courseService: CourseService,
  private val registrationService: RegistrationService,
) : RegistrationResolver {

  private val LOG = KotlinLogging.logger { }

  @BatchMapping(typeName = "Registration")
  fun student(input: List<RegistrationDto>): Mono<Map<RegistrationDto, StudentDto>> {

    val registrations: Flux<Registration> = registrationService.dao.findAllById(input.toFlux().map { it.id }.distinct())
    // this way will only query registrations once
    return registrations.collectMap { it.id }.flatMap { rm: Map<Int, Registration> ->
      // reuse rm object
      studentService.dao.findAllById(rm.values.map { it.studentId }.distinct()).collectMap { it.id }
        .flatMap { sm: Map<Int, Student> ->
          input.toFlux()
            .map { it to StudentMapper.I.from(sm[rm[it.id]!!.studentId]!!) }
            .collectMap()
        }
    }
  }

  override fun student(registration: RegistrationDto, env: DataFetchingEnvironment): Mono<StudentDto> {
    TODO("Not yet implemented")
  }

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

  override fun delete(registration: RegistrationDto, env: DataFetchingEnvironment): Mono<Boolean> =
    this.registrationService.deleteById(registration.id)
}
