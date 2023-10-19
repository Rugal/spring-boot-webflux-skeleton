package ga.rugal

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

/**
 * Main entrance for micro service.
 *
 * @author Rugal Bernstein
 */
@SpringBootApplication
open class MainEntrance

fun main(args: Array<String>) {
  runApplication<MainEntrance>(*args)
}
