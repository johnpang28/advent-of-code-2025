import Day02.isInvalid1
import Day02.isInvalid2
import Day02.parse
import Day02.solve

fun main() {

    fun part1(input: List<LongRange>): Long = input.solve { it.isInvalid1() }

    fun part2(input: List<LongRange>): Long = input.solve { it.isInvalid2() }

    val input = parse(readInput("Day02").first())
    part1(input).println()
    part2(input).println()
}

object Day02 {

    fun parse(s: String): List<LongRange> =
        s.split(",").map {
            it.split("-").let { (id1, id2) -> id1.toLong()..id2.toLong() }
        }

    fun Long.isInvalid1(): Boolean = toString().let {
        val mid = it.length / 2
        it.take(mid) == it.drop(mid)
    }

    fun Long.isInvalid2(): Boolean = toString().let { s ->
        (1..s.length / 2).map { i ->
            "^(${s.take(i)})*$".toRegex().matches(s)
        }.firstOrNull { it } ?: false
    }

    fun List<LongRange>.solve(f: (Long) -> Boolean) = flatMap { it.filter { f(it) } }.sum()
}
