enum class PlayOption {
    ROCK, PAPER, SCISSOR;

    fun score(): Int {
        return this.ordinal + 1
    }

    fun result(another: PlayOption): GameResult {
        val res = if (this == another) GameResult.DRAW
            else if (this == ROCK && another == PAPER)  GameResult.LOST
            else if (this == PAPER && another == SCISSOR)  GameResult.LOST
            else if (this == SCISSOR && another == ROCK)  GameResult.LOST
            else GameResult.WIN
        return res
    }
}

enum class GameResult(val value: Int) {
    LOST(0), DRAW(3), WIN(6)
}

data class GamePlay(val opponentPlay: PlayOption, val myPlay: PlayOption) {
    fun getScore(): Int {
        return myPlay.score() + myPlay.result(opponentPlay).value
    }
}



fun main() {

    fun stringToOpponentPlay(play: String) = when(play) {
        "A" -> PlayOption.ROCK
        "B" -> PlayOption.PAPER
        "C" -> PlayOption.SCISSOR
        else -> throw Exception("opponent - value not valid")
    }

    fun stringToMyPlayPart01(play: String) = when(play) {
        "X" -> PlayOption.ROCK
        "Y" -> PlayOption.PAPER
        "Z" -> PlayOption.SCISSOR
        else -> throw Exception("my play - value not valid")
    }

    fun stringToMyPlayPart02(play: String, opponentPlay: PlayOption): PlayOption {
        return  when(play) {
            // loose
            "X" -> when (opponentPlay) {
                PlayOption.ROCK -> PlayOption.SCISSOR
                PlayOption.PAPER -> PlayOption.ROCK
                PlayOption.SCISSOR -> PlayOption.PAPER
            }
            // draw
            "Y" -> opponentPlay
            // wing
            "Z" -> when (opponentPlay) {
                PlayOption.ROCK -> PlayOption.PAPER
                PlayOption.PAPER -> PlayOption.SCISSOR
                PlayOption.SCISSOR -> PlayOption.ROCK
            }
            else -> throw Exception("bad input ")
        }
    }

    fun part21(input: List<String>): Int {
        val gamePlays = input.map {
            val (opponent, me) = it.split(" ")
            GamePlay(stringToOpponentPlay(opponent), stringToMyPlayPart01(me))
        }

        val res = gamePlays.fold(0) { sum, element -> sum + element.getScore() }
        return res
    }

    fun part22(input: List<String>): Int {
        val gamePlays = input.map {
            val (opponent, me) = it.split(" ")
            val opponentPlay = stringToOpponentPlay(opponent)
            GamePlay(stringToOpponentPlay(opponent), stringToMyPlayPart02(me, opponentPlay))
        }

        val res = gamePlays.fold(0) { sum, element -> sum + element.getScore() }
        return res
    }

    val input = readInput("Day02_test")
    println("total: ${part21(input)}")

    println("Part two")
    println(part22(input))

    // test if implementation meets criteria from the description, like:
//    val testInput = readInput("Day02_test")
//    check(part2(testInput) == 45000)

}
