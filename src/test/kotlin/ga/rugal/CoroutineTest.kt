package ga.rugal

import kotlin.test.Test
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class CoroutineTest {
  private val LOG = KotlinLogging.logger { }

  suspend fun rugal() {
    withContext(Dispatchers.IO) {
      delay(2000)
      LOG.info { 3 }
    }
  }

  @Test
  fun testCoroutine() {
    runBlocking {
      listOf(1, 2, 3).map {
        async {
          delay(it * 1000L)
          it
        }
      }
        .awaitAll()
        .map {
          LOG.info { it }
        }
    }
  }
}
