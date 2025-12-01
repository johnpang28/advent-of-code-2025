import Day01.Dial
import Day01.Direction.Left
import Day01.Direction.Right
import Day01.Instruction
import Day01.parse

fun main() {

    fun part1(input: List<Instruction>): Int {
        val dial = Dial()
        return input.map { dial.rotate(it).value }.count { it == 0 }
    }

    fun part2(input: List<Instruction>): Int =
        Dial().apply {
            input.forEach { rotate(it) }
        }.clicks

    val input = readInput("Day01").map { parse(it) }
    part1(input).println()
    part2(input).println()
}

object Day01 {

    enum class Direction { Left, Right }

    data class Instruction(val direction: Direction, val distance: Int)

    fun parse(s: String): Instruction {
        val distance = s.drop(1).toInt()
        return when (s.first()) {
            'L' -> Instruction(Left, distance)
            'R' -> Instruction(Right, distance)
            else -> error("Can't parse $s")
        }
    }

    class Dial {
        val elements: List<DialElement> = (0..99).map { DialElement(it) }
            .apply {
                zipWithNext().forEach { (a, b) ->
                    a.right = b
                    b.left = a
                }
                first().left = last()
                last().right = first()
            }
        var currentElement = elements[50]
        var clicks = 0

        fun rotate(instruction: Instruction): DialElement =
            rotate(instruction.distance) {
                when (instruction.direction) {
                    Left -> it.left!!
                    Right -> it.right!!
                }
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