


fun main() {
    fun getPriority(letter: Char): Int {
        return if (letter.isLowerCase()) {
            val range = 'a'..'z'
            val res = range.indexOfFirst { letter == it }
            res + 1
        } else {
            val range = 'A'..'Z'
            val res = range.indexOfFirst { letter == it }
            res + 27
        }
    }

    fun getCompartments(rucksack: String): Pair<String, String> {
        val endIndex = rucksack.length/2

        val first = rucksack.substring(0, endIndex)
//        println("first: $first")

        val second = rucksack.substring(endIndex, rucksack.length)
//        println("second: $second")

        return first to second
    }

    fun getFoundLetter(compartments: Pair<String, String>): Char {
        var foundLetter = 'X'
        for (letter in compartments.first) {
            for (letter2 in compartments.second) {
                if (letter == letter2) {
                    foundLetter = letter
                    break
                }
            }
        }
        return foundLetter
    }

    fun part31(input: List<String>): Int {
        val priorities = input.map { rucksack ->
            val comps = getCompartments(rucksack)
            val foundLetter = getFoundLetter(comps)
            println(foundLetter)
            getPriority(foundLetter)
        }

        println(priorities)

        val sum = priorities.fold(0) { sum, p -> sum + p}
        return sum
    }

    fun part32(input: List<String>): Int {

        val commonLetters = mutableListOf<Char>()
        for (index in 0..input.lastIndex step  3) {
            println("index: $index")

            val v1 = input[index]
            val v2 = input[index+1]
            val v3 = input[index+2]

            //encontrar commun
            var commonLetter = 'X'
            for (letter in v1) {
                if (letter in v2 && letter in v3) {
                    commonLetter = letter
                    break
                }
            }
            println("commonLetter $index: $commonLetter")
            commonLetters.add(commonLetter)
        }

        val res = commonLetters.map { getPriority(it) }.sum()
        return res


//        val priorities = input.map { rucksack ->
//            val comps = getCompartments(rucksack)
//            val foundLetter = getFoundLetter(comps)
//            println(foundLetter)
//            getPriority(foundLetter)
//        }
//
//        println(priorities)
//
//        val sum = priorities.fold(0) { sum, p -> sum + p}
//        return sum
//        return input.size
    }

//    val resa = getPriority('Z')
//    println("priority a: $resa")

    val input = readInput("Day03_test")
    println("total: ${part31(input)}")

    println("Part two")
    println("total parte 2: ${part32(input)}")

    // test if implementation meets criteria from the description, like:
//    val testInput = readInput("Day02_test")
//    check(part2(testInput) == 45000)

}
