package ga.rugal.reactor.springmvc.graphql

import ga.rugal.r2dbc.graphql.CourseDto
import ga.rugal.r2dbc.graphql.QueryResolver
import ga.rugal.r2dbc.graphql.RegistrationDto
import ga.rugal.r2dbc.graphql.StudentDto
import ga.rugal.r2dbc.graphql.TagDto
import ga.rugal.reactor.core.service.CourseService
import ga.rugal.reactor.core.service.RegistrationService
import ga.rugal.reactor.core.service.StudentService
import ga.rugal.reactor.core.service.TagService
import ga.rugal.reactor.springmvc.mapper.TagMapper
import graphql.schema.DataFetchingEnvironment
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Controller
import reactor.core.publisher.Mono

@Controller
class RootQuery(
  private val tagService: TagService,
  private val studentService: StudentService,
  private val courseService: CourseService,
  private val registrationService: RegistrationService,
) : QueryResolver {
  private val LOG = KotlinLogging.logger {}

  override fun getTag(id: Int, env: DataFetchingEnvironment): Mono<TagDto> = this.tagService
    .findById(id)
    .map { TagMapper.I.from(it) }

  override fun getStudent(id: Int, env: DataFetchingEnvironment): Mono<StudentDto> {
    TODO("Not yet implemented")
  }

  override fun getCourse(id: Int, env: DataFetchingEnvironment): Mono<CourseDto> {
    TODO("Not yet implemented")
  }

  override fun getRegistration(id: Int, env: DataFetchingEnvironment): Mono<RegistrationDto> {
    TODO("Not yet implemented")
  }
}
