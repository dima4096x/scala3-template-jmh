package benchmark

import java.util.concurrent.TimeUnit
import org.openjdk.jmh.annotations.*
import org.openjdk.jmh.infra.Blackhole

/**
 * run all: sbt bench / Jmh / run
 * run a class: sbt bench / Jmh / run .*SumBenchmark.*
 * run a specific benchmark: sbt bench / Jmh / run benchmark.SumBenchmark.sumArray
 */

@State(Scope.Benchmark)
@BenchmarkMode(Array(Mode.Throughput))
@OutputTimeUnit(TimeUnit.SECONDS)
@Fork(1)
@Warmup(iterations = 10, time = 10, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 10, time = 10, timeUnit = TimeUnit.SECONDS)
@Timeout(time = 60, timeUnit = TimeUnit.SECONDS)
@Threads(value = 1)
class SumBenchmark:

  val size = 10000000

  var list: List[Long] = _
  var array: Array[Long] = _
  var vector: Vector[Long] = _

  @Setup(Level.Iteration)
  def setupData(): Unit =
    list = List.fill(size)(scala.util.Random.nextLong(Long.MaxValue))
    array = list.toArray
    vector = list.toVector

  @Benchmark
  def sumArray(blackhole: Blackhole): Unit =
    blackhole.consume(array.sum)

  @Benchmark
  def sumList(blackhole: Blackhole): Unit =
    blackhole.consume(list.sum)

  @Benchmark
  def sumVector(blackhole: Blackhole): Unit =
    blackhole.consume(vector.sum)
