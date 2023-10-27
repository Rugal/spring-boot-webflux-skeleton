package ga.rugal.reactor.springmvc.graphql

import ga.rugal.r2dbc.graphql.StudentDto
import ga.rugal.r2dbc.graphql.StudentResolver
import ga.rugal.r2dbc.graphql.UpdateStudentDto
import ga.rugal.reactor.core.service.StudentService
import ga.rugal.reactor.springmvc.mapper.StudentMapper
import graphql.schema.DataFetchingEnvironment
import org.springframework.stereotype.Controller
import reactor.core.publisher.Mono

@Controller
class StudentFieldResolver(private val studentService: StudentService) : StudentResolver {

  override fun delete(dto: StudentDto, env: DataFetchingEnvironment): Mono<Boolean> = this.studentService
    .deleteById(dto.id)

  override fun update(dto: StudentDto, input: UpdateStudentDto, env: DataFetchingEnvironment): Mono<StudentDto> =
    this.studentService.dao
      .findById(dto.id)
      .map { it.copy(name = input.name) }
      .flatMap { this.studentService.dao.save(it) }
      .map { StudentMapper.I.from(it) }
}
