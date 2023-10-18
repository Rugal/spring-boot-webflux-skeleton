package ga.rugal.reactor.core.entity

import java.time.Instant
import jakarta.persistence.Column
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.PrePersist
import jakarta.persistence.PreUpdate
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table(name = "registration")
data class Registration(
  @Id val id: Int,

  @JoinColumn(name = "student_id", nullable = false)
  @ManyToOne
  val student: Student? = null,

  @JoinColumn(name = "course_id", nullable = false)
  @ManyToOne
  val course: Course? = null,

  val score: Int? = null,

  @Column(name = "create_at")
  var createAt: Long = Instant.now().epochSecond,

  @Column(name = "update_at")
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
