package ga.rugal.reactor.core.entity

import org.springframework.data.annotation.Id

data class Tag(
  @Id var id: Int? = null,
  var name: String? = null,
) {
}
