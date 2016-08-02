# Kafka-related benchmarks
this project contains [jmh-based](http://openjdk.java.net/projects/code-tools/jmh/) microbenchmarks for various kafka-related classes.
# Building
`> mvn clean install`
# Running
`> java -jar target/benchmarks.jar -wi 5 -i 5 -f 1`
# Example output
on a xeon E5-2620v3 @ 2.40GHz:
```
Benchmark                                                Mode  Cnt            Score           Error  Units
ArrayBlockingQueueBenchmark.bob:testConsume             thrpt    5      5185607.957 ±    846130.823  ops/s
ArrayBlockingQueueBenchmark.bob:testProduce             thrpt    5      5189194.400 ±    833782.756  ops/s
BaselineBenchmark.bob:testConsume                       thrpt    5   9023300537.194 ± 717369559.470  ops/s
BaselineBenchmark.bob:testProduce                       thrpt    5   5680766513.264 ± 702148553.079  ops/s
BaselineBenchmark.testSimpleCall                        thrpt    5   3074140248.084 ± 122239421.702  ops/s
ByteBoundedBlockingQueue2Benchmark.bob:testConsume      thrpt    5       164570.017 ±    119941.664  ops/s
ByteBoundedBlockingQueue2Benchmark.bob:testProduce      thrpt    5       164794.897 ±    120397.293  ops/s
ByteBoundedBlockingQueueBenchmark.bob:testConsume       thrpt    5       379922.895 ±    347814.208  ops/s
ByteBoundedBlockingQueueBenchmark.bob:testProduce       thrpt    5       379553.331 ±    346405.281  ops/s
MetricBoundArrayBlockingQueueBenchmark.bob:testConsume  thrpt    5      5238290.425 ±    915740.977  ops/s
MetricBoundArrayBlockingQueueBenchmark.bob:testProduce  thrpt    5      5238614.057 ±    927343.567  ops/s
