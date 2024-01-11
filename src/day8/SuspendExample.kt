package day8

import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlin.random.Random
import kotlin.time.Duration.Companion.milliseconds

class SuspendExample {
    suspend fun executeManyCoroutinesInParallelUsingAsync(): List<Int> {
        val result = coroutineScope {
            (1..5).map { n ->
                async {
                    val delay = Random.nextInt(100, 1000)
                    delay(delay.milliseconds)
                    println("- processing $n")
                    n * n
                }
            }.awaitAll()
        }
        println("Result: $result")
        return result
    }
}

suspend fun main() {
    SuspendExample().executeManyCoroutinesInParallelUsingAsync()
}
