package ga.rugal.reactor.core.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table
data class Tag(
  @Id var id: Int? = null,
  var name: String? = null,
) {
}
