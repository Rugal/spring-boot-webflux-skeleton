package ga.rugal.reactor

import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux

class ReactorErrorTest {
  /*
input=1
input=2
java.lang.ArithmeticException: / by zero
sum=2     <-- stop after having exception
   */
  /**
   * Stop getting from upstream but still process to downstream
   */
  @Test
  fun errorResume() {
    Flux.range(1, 5)
      .doOnNext { println("input=$it") }
      .map { if (it == 2) it / 0 else it }
      .map { it * 2 }
      .onErrorResume {
        println(it)
        Flux.empty()
      }
      .reduce { i: Int, j: Int -> i + j }
      .doOnNext { println("sum=$it") }
      .block()
  }

  /*
input=1
input=2
java.lang.ArithmeticException: / by zero
onErrorContinue=2
input=3
input=4
input=5
sum=26       <- this value ignored input=2
   */
  /**
   * Skip the error element, anything else keeps the same as if there is no error happen at all
   */
  @Test
  fun errorContinue() {
    Flux.range(1, 5)
      .doOnNext { println("input=$it") }
      .map { if (it == 2) it / 0 else it }
      .map { it }
      .onErrorContinue { err, i ->
        println(err)
        println("onErrorContinue=${i}")
      }
      .reduce { i: Int, j: Int -> i + j }
      .doOnNext { println("sum=$it") }
      .block()
  }

  /*
input=1
Rugal
input=2
Rugal
input=3
17:08:28.484 [main] ERROR reactor.core.publisher.Operators -- Operator called default onErrorDropped
reactor.core.Exceptions$ErrorCallbackNotImplemented: java.lang.ArithmeticException: / by zero
Caused by: java.lang.ArithmeticException: / by zero
   */
  @Test
  fun errorStop() {
    Flux.range(1, 5)
      .doOnNext { println("input=$it") }
      .map { if (it == 3) it / 0 else it }
      .onErrorStop()
      .doOnNext { println("Rugal") }
      .map { it }
      .reduce { i: Int, j: Int -> i + j }
      .doOnNext { println("sum=$it") }
      .subscribe()
  }

  /*
input=1
Rugal
input=2
Rugal
input=3
17:07:12.277 [main] ERROR reactor.core.publisher.Operators -- Operator called default onErrorDropped
reactor.core.Exceptions$ErrorCallbackNotImplemented: java.lang.ArithmeticException: / by zero
Caused by: java.lang.ArithmeticException: / by zero
   */
  @Test
  fun nothing() {
    Flux.range(1, 5)
      .doOnNext { println("input=$it") }
      .map { if (it == 3) it / 0 else it }
      .doOnNext { println("Rugal") }
      .map { it }
      .reduce { i: Int, j: Int -> i + j }
      .doOnNext { println("sum=$it") }
      .subscribe()
  }
}
