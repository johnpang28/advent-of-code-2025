import Day02.isInvalid1
import Day02.isInvalid2
import Day02.parse

fun main() {

    fun part1(input: List<LongRange>): Long = input.flatMap { it.filter { it.isInvalid1() } }.sum()

    fun part2(input: List<LongRange>): Long = input.flatMap { it.filter { it.isInvalid2() } }.sum()

    val input = parse(readInput("Day02").first())
    part1(input).println()
    part2(input).println()
}

object Day02 {

    fun parse(s: String): List<LongRange> =
        s.split(",").map {
            it.split("-").let { (first, second) -> first.toLong()..second.toLong() }
        }

    fun Long.isInvalid1(): Boolean = toString().let {
        val mid = it.length / 2
        it.take(mid) == it.drop(mid)
    }

    fun Long.isInvalid2(): Boolean = toString().let {
        (1..it.length / 2).map { i ->
            "^(${it.take(i)})*$".toRegex().matches(it)
        }.firstOrNull { it } ?: false
    }
}
