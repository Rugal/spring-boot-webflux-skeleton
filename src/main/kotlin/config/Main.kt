package config

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan

/**
 * Main entrance for micro service.
 *
 * @author Rugal Bernstein
 */
@SpringBootApplication
open class Main {

  companion object {
    @JvmStatic
    fun main(args: Array<String>) {
      SpringApplication.run(Main::class.java, *args)
    }
  }
}
