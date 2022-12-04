


fun main() {

//    fun <Int> Pair<Int, Int>.fullyContains(another: Pair<Int, Int>): Boolean {
//        return another.first <= first && another.second <= second
//    }

    fun fullyContains(one: Pair<Int, Int>, another: Pair<Int, Int>): Boolean {
        return one.first <= another.first && one.second >= another.second
    }

    fun overlap(one: Pair<Int, Int>, another: Pair<Int, Int>): Boolean {

        var res = false

        val secondRange = another.second..another.second
        for (i in one.first..one.second) {
            if (secondRange.contains(i)) {
                res = true
                break
            }
        }
        return res
    }

    fun parseInput(input: List<String>) = input.map {
        val (section1, section2) = it.split(",")
        val (s1p1, s1p2) = section1.split("-")
        val (s2p1, s2p2) = section2.split("-")

        //listOf( Pair(s1p1.toInt(), s1p2.toInt()) , Pair(s2p1.toInt(), s2p2.toInt()))
        Pair(s1p1.toInt(), s1p2.toInt()) to Pair(s2p1.toInt(), s2p2.toInt())
    }

    fun part41(input: List<String>): Int {
        val sections = parseInput(input)

        val counter = sections.fold(0) { acc, element ->
            if ( fullyContains(element.first, element.second )
                || fullyContains(element.second, element.first)) {
                acc + 1
            } else acc
        }
        return counter
    }

    fun part42(input: List<String>): Int {
        val sections = parseInput(input)

        val counter = sections.fold(0) { acc, element ->
            if ( overlap(element.first, element.second )
                || overlap(element.second, element.first)) {
//                println("first: ${element.first} second: ${element.second}")
                acc + 1
            } else acc
        }
        return counter
//        return input.size
    }

//    val test1  = fullyContains(Pair(2,8), 3 to 7)
//    println("test1: $test1")
//
//    val test2  = fullyContains(Pair(4,6), 6 to 6)
//    println(test2)

    for (i in 5..7) {
        println("range: $i")
    }

    val input = readInput("Day04_test")
    println("total: ${part41(input)}")

    println("Part two")
    println("total parte 2: ${part42(input)}")

    // test if implementation meets criteria from the description, like:
//    val testInput = readInput("Day02_test")
//    check(part2(testInput) == 45000)

}
