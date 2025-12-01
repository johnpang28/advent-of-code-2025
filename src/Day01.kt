import Day01.Dial
import Day01.Direction.*
import Day01.Rotation
import Day01.parse

fun main() {

    fun part1(input: List<Rotation>): Int {
        val dial = Dial()
        return input.map { dial.rotate(it).value }.count { it == 0 }
    }

    fun part2(input: List<Rotation>): Int =
        Dial().apply {
            input.forEach { rotate(it) }
        }.clicks

    val input = readInput("Day01").map { parse(it) }
    part1(input).println()
    part2(input).println()
}

object Day01 {

    enum class Direction { Left, Right }

    data class Rotation(val direction: Direction, val distance: Int)

    fun parse(s: String): Rotation {
        val distance = s.drop(1).toInt()
        return when (s.first()) {
            'L' -> Rotation(Left, distance)
            'R' -> Rotation(Right, distance)
            else -> error("Can't parse $s")
        }
    }

    class Dial() {
        val elements: List<DialElement> = (0..99).map {
            DialElement(it)
        }.apply {
            zipWithNext().forEach { (a, b) ->
                a.right = b
                b.left = a
            }
            first().left = last()
            last().right = first()
        }
        var currentElement = elements[50]
        var clicks = 0

        fun rotate(rotation: Rotation): DialElement =
            when (rotation.direction) {
                Left -> rotate(rotation.distance) { it.left!! }
                Right -> rotate(rotation.distance) { it.right!! }
            }

        fun rotate(distance: Int, rotate: (DialElement) -> DialElement): DialElement {
            repeat(distance) {
                currentElement = rotate(currentElement)
                if (currentElement.value == 0) clicks++
            }
            return currentElement
        }
    }

    data class DialElement(val value: Int, var right: DialElement? = null, var left: DialElement? = null) {
        override fun toString(): String = "DialElement(value=$value, right=${right?.value}, left=${left?.value})"
    }
}