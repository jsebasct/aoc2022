fun main() {
    fun part1(input: List<String>): Int {
        val caloriesElf = mutableListOf<Int>()

        var sum = 0
        for (calorie in input) {
            if (calorie != "") {
                sum += calorie.toInt()
            } else {
                caloriesElf.add(sum)
                sum = 0
            }
        }

        return caloriesElf.maxOrNull() ?: 0
    }

    fun part2(input: List<String>): Int {
        val caloriesElf = mutableListOf<Int>()

        var sum = 0
        for (calorie in input) {
            if (calorie != "") {
                sum += calorie.toInt()
            } else {
                caloriesElf.add(sum)
                sum = 0
            }
        }
        caloriesElf.add(sum)
        caloriesElf.sortDescending()

        return caloriesElf.take(3).sum()
    }

    val input = readInput("Day01_test")
    println(part1(input))
    println("Part two")
    println(part2(input))

    // test if implementation meets criteria from the description, like:
//    val testInput = readInput("Day01_test")
//    check(part2(testInput) == 45000)
}
