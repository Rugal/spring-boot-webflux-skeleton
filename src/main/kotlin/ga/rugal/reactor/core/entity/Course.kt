package ga.rugal.reactor.core.entity

import java.time.Instant
import jakarta.persistence.Column
import jakarta.persistence.PrePersist
import jakarta.persistence.PreUpdate
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table(name = "course")
data class Course(
  @Id val id: Int,

  val name: String,
  
  var createAt: Long = Instant.now().epochSecond,

  var updateAt: Long = Instant.now().epochSecond,
) {

  @PrePersist
  fun onCreate() {
    createAt = Instant.now().epochSecond
    updateAt = createAt
  }

  @PreUpdate
  fun onUpdate() {
    updateAt = Instant.now().epochSecond
  }
}
