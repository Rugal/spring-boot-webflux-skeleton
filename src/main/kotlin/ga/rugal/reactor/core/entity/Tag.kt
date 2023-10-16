package ga.rugal.reactor.core.entity

import ga.rugal.config.SystemDefaultProperty
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table(name = "tag")
data class Tag(
  @Id val id: Int,
  val name: String,
) {
}
