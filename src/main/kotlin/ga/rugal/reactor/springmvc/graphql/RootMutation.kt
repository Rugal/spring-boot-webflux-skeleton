package ga.rugal.reactor.springmvc.graphql

import ga.rugal.r2dbc.graphql.CourseDto
import ga.rugal.r2dbc.graphql.MutationResolver
import ga.rugal.r2dbc.graphql.NewCourseDto
import ga.rugal.r2dbc.graphql.NewRegistrationDto
import ga.rugal.r2dbc.graphql.NewStudentDto
import ga.rugal.r2dbc.graphql.NewTagDto
import ga.rugal.r2dbc.graphql.RegistrationDto
import ga.rugal.r2dbc.graphql.StudentDto
import ga.rugal.r2dbc.graphql.TagDto
import ga.rugal.reactor.core.service.CourseService
import ga.rugal.reactor.core.service.RegistrationService
import ga.rugal.reactor.core.service.StudentService
import ga.rugal.reactor.core.service.TagService
import ga.rugal.reactor.springmvc.mapper.CourseMapper
import ga.rugal.reactor.springmvc.mapper.StudentMapper
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
class RootMutation(
  private val tagService: TagService,
  private val studentService: StudentService,
  private val courseService: CourseService,
  private val registrationService: RegistrationService,
) : MutationResolver {
  private val LOG = KotlinLogging.logger {}

  override fun createTag(input: NewTagDto, env: DataFetchingEnvironment): Mono<TagDto> = Mono.just(input)
    .map { TagMapper.I.to(it) }
    .flatMap { tagService.tagDao.save(it) }
    .map { TagMapper.I.from(it) }

  override fun createStudent(input: NewStudentDto, env: DataFetchingEnvironment): Mono<StudentDto> = Mono.just(input)
    .map { StudentMapper.I.to(input) }
    .flatMap { this.studentService.dao.save(it) }
    .map { StudentMapper.I.from(it) }

  override fun createCourse(input: NewCourseDto, env: DataFetchingEnvironment): Mono<CourseDto> = Mono.just(input)
    .map { CourseMapper.I.to(input) }
    .flatMap { this.courseService.dao.save(it) }
    .map { CourseMapper.I.from(it) }

  override fun createRegistration(input: NewRegistrationDto?, env: DataFetchingEnvironment?): Mono<RegistrationDto> {
    TODO("Not yet implemented")
  }
}
