package ga.rugal.reactor.springmvc.mapper

import ga.rugal.r2dbc.graphql.NewStudentDto
import ga.rugal.r2dbc.graphql.StudentDto
import ga.rugal.reactor.core.entity.Student
import ga.rugal.reactor.core.entity.Tag
import org.mapstruct.Mapper
import org.mapstruct.NullValuePropertyMappingStrategy
import org.mapstruct.factory.Mappers

/**
 * The Data Mapper For Contact.
 *
 * @author Rugal Bernstein
 */
@Mapper(
  config = CentralConfig::class,
  nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
)
interface StudentMapper {
  fun from(u: Student): StudentDto

  fun to(newUserDto: NewStudentDto): Student

  companion object {
    @JvmField
    val I = Mappers.getMapper(StudentMapper::class.java)
  }
}
