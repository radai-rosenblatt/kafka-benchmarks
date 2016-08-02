package net.radai.kafka

import java.util.concurrent.{ArrayBlockingQueue, TimeUnit}

import net.radai.kafka.BaselineBenchmark.Payload
import org.openjdk.jmh.annotations._

@State(Scope.Benchmark)
@BenchmarkMode(Array(Mode.Throughput, Mode.AverageTime))
class BaselineBenchmark {
  //shared global state
  val queue: ArrayBlockingQueue[Payload] = new ArrayBlockingQueue[Payload](10000)

  @Setup(Level.Iteration)
  def setup(): Unit = {
    queue.clear()
  }

  @Benchmark
  def testSimpleCall(): Unit = {
    //nop
  }

  @Benchmark
  @Group("bob")
  @GroupThreads(16) //num.network.threads = 3 by deault
  def testProduce(payload: Payload): Unit = {
    //nop
  }

  @Benchmark
  @Group("bob")
  @GroupThreads(24) //num.io.threads = 8 by default
  def testConsume(): Unit = {
    //nop
  }
}

object BaselineBenchmark {
  @State(Scope.Thread)
  class Payload {
    //empty
  }
}