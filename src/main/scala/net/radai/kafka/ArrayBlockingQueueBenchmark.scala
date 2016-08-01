package net.radai.kafka

import java.util.concurrent.{ArrayBlockingQueue, TimeUnit}

import kafka.utils.ByteBoundedBlockingQueue
import net.radai.kafka.ArrayBlockingQueueBenchmark.Payload
import org.openjdk.jmh.annotations._

@State(Scope.Benchmark)
@BenchmarkMode(Array(Mode.Throughput))
class ArrayBlockingQueueBenchmark {
  //shared global state
  val queue: ArrayBlockingQueue[Payload] = new ArrayBlockingQueue[Payload](10000)

  @Setup(Level.Iteration)
  def setup(): Unit = {
    queue.clear()
  }

  @Benchmark
  def testBaseline(): Unit = {
    //nop
  }

  @Benchmark
  @Group("bob")
  @GroupThreads(16) //num.network.threads = 3 by deault
  def testProduce(payload: Payload): Unit = {
    queue.offer(payload, 300, TimeUnit.MILLISECONDS) //RequestChannel.sendRequest()
  }

  @Benchmark
  @Group("bob")
  @GroupThreads(24) //num.io.threads = 8 by default
  def testConsume(): Unit = {
    val payload: Payload = queue.poll(300, TimeUnit.MILLISECONDS) //KafkaRequestHandler.run()
  }
}

object ArrayBlockingQueueBenchmark {
  @State(Scope.Thread)
  class Payload {
    //empty
  }
}

