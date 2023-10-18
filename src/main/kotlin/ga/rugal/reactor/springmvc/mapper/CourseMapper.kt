package ga.rugal.reactor.springmvc.mapper

import ga.rugal.r2dbc.graphql.CourseDto
import ga.rugal.r2dbc.graphql.NewCourseDto
import ga.rugal.reactor.core.entity.Course
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
interface CourseMapper {
  fun from(u: Course): CourseDto

  fun to(newUserDto: NewCourseDto): Tag

  companion object {
    @JvmField
    val I = Mappers.getMapper(CourseMapper::class.java)
  }
}
