package ga.rugal.reactor.springmvc.mapper

import ga.rugal.r2dbc.graphql.NewTagDto
import ga.rugal.r2dbc.graphql.TagDto
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
interface TagMapper {
  fun from(u: Tag): TagDto

  fun to(newUserDto: NewTagDto): Tag

  companion object {
    @JvmField
    val I = Mappers.getMapper(TagMapper::class.java)
  }
}
