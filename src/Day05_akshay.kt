import java.io.File


fun main() {

    data class Movement(val amount: Int, val source: Int, val destination: Int)

    fun getMovements(movementLines: String) = movementLines.split("\n")
        .map { line ->
            val splintedLine = line.split(" ")
                .filter { it.toIntOrNull() != null }
                .map { it.toInt() }
            Movement(splintedLine[0], splintedLine[1], splintedLine[2])
        }

    fun getCrates(stacksLines: String): MutableList<ArrayDeque<Char>> {
        val stacksHeadDown = stacksLines.lines().reversed()
        println(stacksHeadDown.joinToString(separator = "\n"))

        val notNullIndices = stacksHeadDown[0].indices.filter { indice -> stacksHeadDown[0].get(indice).digitToIntOrNull() != null}
        println(notNullIndices)

        val crateNumber = stacksHeadDown[0][notNullIndices[notNullIndices.lastIndex]]

        val crates = mutableListOf<ArrayDeque<Char>>()
        repeat(crateNumber.digitToInt()) {
            crates.add(ArrayDeque<Char>())
        }
        stacksHeadDown.forEachIndexed { index, crateLine ->
            if (index > 0) {

                if (index == stacksHeadDown.lastIndex) {
                    println("this is the line: ##$crateLine##")
                    println("this is the line: ##${crateLine.lastIndex}")
                }

                for (createIndex in 0 until crateNumber.digitToInt()) {
//                    if (index == stacksHeadDown.lastIndex) {
//                        println("##$crateLine## - not null iondex: ${notNullIndices[createIndex]}-")
//                    }
                    //println("createIndex: $createIndex with value: -${crateLine[notNullIndices[createIndex]]}-")
                    val crateValue = if (crateLine.lastIndex >= notNullIndices[createIndex]) crateLine[notNullIndices[createIndex]] else ' '
//                    val crateValue = crateLine[notNullIndices[createIndex]]
                    if (crateValue != ' ') {
                        crates[createIndex].add(crateLine[notNullIndices[createIndex]])
                    }
                }
            }
        }
        println("\ncrates")
        println(crates)

        return crates
    }

    fun part51(input: String): String {
        val (stacksLines, movementLines) = input.split("\n\n")

        val movements = getMovements(movementLines)
        val crates = getCrates(stacksLines)



        // make movements
        movements.forEach { move ->
            println(move)
            repeat(move.amount) {
                crates[move.destination - 1].add(crates[move.source - 1].removeLast())
            }
        }
        println("After movements")
        println(crates)

        val res = crates.map {
            it.last()
        }.joinToString(separator = "")

        println(res)
        return res
    }


    fun part52(input: String): String {
        val (stacksLines, movementLines) = input.split("\n\n")

        val movements = getMovements(movementLines)
        val crates = getCrates(stacksLines)

        println("\ncrates")
        println(crates)

        // make movements
        movements.forEach { move ->
//            println(move)
            val temp = mutableListOf<Char>()

            repeat(move.amount) {
                temp.add(crates[move.source - 1].removeLast())
            }
//            println("temp: $temp")
            crates[move.destination - 1].addAll(temp.reversed())
        }
        println("After movements")
        println(crates)

        val res = crates.map {
            it.last()
        }.joinToString(separator = "")

//        println(res)
        return res
    }
//    val test1  = fullyContains(Pair(2,8), 3 to 7)
//    println("test1: $test1")
//
//    val test2  = fullyContains(Pair(4,6), 6 to 6)
//    println(test2)


//    val input = readInput("Day05_test")
//    println("total part 1: ${part51(input)}")
    val input = File("src", "Day05_test.txt").readText()
    println("total: ${part51(input)}")

    println("Part two")
    println("total parte 2: ${part52(input)}")

    // test if implementation meets criteria from the description, like:
//    val testInput = readInput("Day02_test")
//    check(part2(testInput) == 45000)

}
