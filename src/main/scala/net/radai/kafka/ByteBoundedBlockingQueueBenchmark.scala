/*
 * Copyright (c) 2014, Oracle America, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 *  * Neither the name of Oracle nor the names of its contributors may be used
 *    to endorse or promote products derived from this software without
 *    specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */

package net.radai.kafka

import java.util.concurrent.TimeUnit

import kafka.utils.ByteBoundedBlockingQueue
import net.radai.kafka.ByteBoundedBlockingQueueBenchmark.Payload
import org.openjdk.jmh.annotations._

@State(Scope.Benchmark)
@BenchmarkMode(Array(Mode.Throughput))
class ByteBoundedBlockingQueueBenchmark {

  //shared global state
  val queue: ByteBoundedBlockingQueue[Payload] = new ByteBoundedBlockingQueue(10000, 10000, Some(_ => 1))

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

object ByteBoundedBlockingQueueBenchmark {
  @State(Scope.Thread)
  class Payload {
    //empty
  }
}

