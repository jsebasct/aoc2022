import java.io.File

fun main() {
    fun parseInput(input: String): List<List<Int>> {
        val data = input.split("\n\n").map { elf ->
            elf.lines().map { it.toInt() }
        }
        return data
    }

    fun part1(input: String): Int {
        val data = parseInput(input)
        return data.maxOf { it.sum() }
    }

    fun part2(input: String): Int {
        val data = parseInput(input)
        return data
            .map { it.sum() }
            .sortedDescending()
            .take(3)
            .sum()
    }

    // test input
    val input = File("src", "Day01_test.txt").readText()
    println(part1(input))
    check(part1(input) == 24000)

    // test if implementation meets criteria from the description, like:
//    val testInput = readInput("Day01_test")
//    check(part2(testInput) == 45000)
}
