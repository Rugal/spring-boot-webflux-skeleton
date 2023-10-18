package ga.rugal.reactor.springmvc.mapper

import ga.rugal.r2dbc.graphql.NewRegistrationDto
import ga.rugal.r2dbc.graphql.RegistrationDto
import ga.rugal.reactor.core.entity.Registration
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
  uses = [CourseMapper::class, StudentMapper::class]
)
interface RegistrationMapper {
  fun from(u: Registration): RegistrationDto

  fun to(newUserDto: NewRegistrationDto): Registration

  companion object {
    @JvmField
    val I = Mappers.getMapper(RegistrationMapper::class.java)
  }
}
