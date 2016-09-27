package net.radai.kafka;

import org.apache.kafka.common.memory.SimpleMemoryPool;
import org.openjdk.jmh.annotations.*;

import java.nio.ByteBuffer;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.Throughput)
public class SimpleMemoryPoolBenchmark {
    private SimpleMemoryPool pool;

    @Setup(Level.Iteration)
    public void setup() {
        pool = new SimpleMemoryPool(100 * 1024 * 1024L, 1024 * 1024, false);
    }

    @Benchmark
    @Group("alloc_1k")
    @GroupThreads(16)
    public void testAllocateRelease1K() {
        ByteBuffer buffer = pool.tryAllocate(1024);
        pool.release(buffer);
    }

    @Benchmark
    @Group("alloc_10k")
    @GroupThreads(16)
    public void testAllocateRelease10K() {
        ByteBuffer buffer = pool.tryAllocate(10 * 1024);
        pool.release(buffer);
    }

    @Benchmark
    @Group("alloc_100k")
    @GroupThreads(16)
    public void testAllocateRelease100K() {
        ByteBuffer buffer = pool.tryAllocate(100 * 1024);
        pool.release(buffer);
    }

    @Benchmark
    @Group("alloc_1m")
    @GroupThreads(16)
    public void testAllocateRelease1M() {
        ByteBuffer buffer = pool.tryAllocate(1024 * 1024);
        pool.release(buffer);
    }
}
