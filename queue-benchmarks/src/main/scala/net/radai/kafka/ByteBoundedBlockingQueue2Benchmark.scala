package net.radai.kafka

import java.util.concurrent.TimeUnit

import net.radai.kafka.ByteBoundedBlockingQueue2Benchmark.Payload
import net.radai.kafka.impls.ByteBoundedBlockingQueue2
import org.openjdk.jmh.annotations._


@State(Scope.Benchmark)
@BenchmarkMode(Array(Mode.Throughput, Mode.AverageTime))
class ByteBoundedBlockingQueue2Benchmark {

  //shared global state
  val queue: ByteBoundedBlockingQueue2[Payload] = new ByteBoundedBlockingQueue2[Payload](10000, 10000, Some(_ => 1))

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

object ByteBoundedBlockingQueue2Benchmark {
  @State(Scope.Thread)
  class Payload {
    //empty
  }
}

