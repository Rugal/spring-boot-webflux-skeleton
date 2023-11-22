package ga.rugal

import kotlin.test.Test
import io.github.oshai.kotlinlogging.KotlinLogging
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Disabled

class LazyTest {
  private val LOG = KotlinLogging.logger { }

  private val lazyValue: String by lazy {
    println("computed!")
    "Hello"
  }

  @Disabled
  @Test
  fun testDelegation() {
    println(lazyValue)
    println(lazyValue)
  }

  @Disabled
  @Test
  fun extension() {
    Assertions.assertEquals(1, 1)
//    1.assertEquals(1)
    1 assertEquals 1
  }

  @Test
  fun testHanoi() {
    hanoi(3)
  }

  private fun hanoi(disk: Int) {
    doHanoi("A", "B", "C", disk)
  }

  private fun doHanoi(from: String, temp: String, to: String, disk: Int) {
    if (disk > 0) {
      doHanoi(from, to, temp, disk - 1)
      LOG.info { "Move disk [$disk]: $from -> $to" }
      doHanoi(temp, from, to, disk - 1)
    }
  }
}

infix fun Any?.assertEquals(actual: Any?) {
  Assertions.assertEquals(this, actual)
}
