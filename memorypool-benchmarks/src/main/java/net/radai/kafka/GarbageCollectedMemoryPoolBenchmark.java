package net.radai.kafka;

import org.apache.kafka.common.memory.GarbageCollectedMemoryPool;
import org.openjdk.jmh.annotations.*;

import java.nio.ByteBuffer;

@State(Scope.Group)
@BenchmarkMode(Mode.Throughput)
public class GarbageCollectedMemoryPoolBenchmark {
    private GarbageCollectedMemoryPool pool;

    @Setup(Level.Iteration)
    public void setup() {
        pool = new GarbageCollectedMemoryPool(100 * 1024 * 1024L, 1024 * 1024, false);
    }

    @TearDown(Level.Iteration)
    public void teardown() throws Exception {
        pool.close();
        pool = null;
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
