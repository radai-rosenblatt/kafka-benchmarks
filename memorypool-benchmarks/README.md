# Memory pool benchmarks
this module benchmarks the performance of different memory pool implementations.
it does so by spawning 16 threads where every thread tries to allocate a buffer and then
immediately releases the buffer back.
# Building
`> mvn clean install`
# Running
notice: this is too short a run to get real numbers

`> java -jar target/benchmarks.jar -wi 1 -w 10 -i 5 -r 30 -f 1 -jvmArgs "-Xmx1G -Xms1G"`
# Example output
on a xeon E5-2620v3 @ 2.40GHz:
```
Benchmark                                        Mode  Cnt        Score        Error  Units
GarbageCollectedMemoryPoolBenchmark.alloc_100k  thrpt    5   198272.519 ±  16045.965  ops/s
GarbageCollectedMemoryPoolBenchmark.alloc_10k   thrpt    5  2781439.307 ± 185287.072  ops/s
GarbageCollectedMemoryPoolBenchmark.alloc_1k    thrpt    5  6029199.952 ± 465936.118  ops/s
GarbageCollectedMemoryPoolBenchmark.alloc_1m    thrpt    5    18464.272 ±    332.861  ops/s
SimpleMemoryPoolBenchmark.alloc_100k            thrpt    5   204240.066 ±   2207.619  ops/s
SimpleMemoryPoolBenchmark.alloc_10k             thrpt    5  3000794.525 ±  83510.836  ops/s
SimpleMemoryPoolBenchmark.alloc_1k              thrpt    5  5893671.778 ± 274239.541  ops/s
SimpleMemoryPoolBenchmark.alloc_1m              thrpt    5    18728.085 ±    792.563  ops/s
