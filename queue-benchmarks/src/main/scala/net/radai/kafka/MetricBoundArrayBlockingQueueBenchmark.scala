package net.radai.kafka

import java.util.concurrent.TimeUnit

import net.radai.kafka.MetricBoundArrayBlockingQueueBenchmark.Payload
import org.openjdk.jmh.annotations._

@State(Scope.Benchmark)
@BenchmarkMode(Array(Mode.Throughput))
class MetricBoundArrayBlockingQueueBenchmark {
  //shared global state
  val queue: MetricBoundedArrayBlockingQueue[Payload] = new MetricBoundedArrayBlockingQueue(
    10000,
    new Metric[Payload] {
      override def measure(instance: Payload): Long = 1
    },
    10000)

  @Setup(Level.Iteration)
  def setup(): Unit = {
    queue.clear()
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

object MetricBoundArrayBlockingQueueBenchmark {
  @State(Scope.Thread)
  class Payload {
    //empty
  }
}