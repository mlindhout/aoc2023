package day02

import kotlin.io.path.Path

enum class Balls(val count: Int) {
    red(12),
    green(13),
    blue(14)
}

data class Turn(val red: Int, val green: Int, val blue: Int) {
    fun valid() = red <= Balls.red.count && green <= Balls.green.count && blue <= Balls.blue.count
}
data class Game(val id: Int, val turns: List<Turn>) {
    fun valid() = turns.all(Turn::valid)
}

fun main() {

    val lines = Path("./src/main/kotlin/day02/input.txt").toFile().useLines { lines ->
        lines.toList()
    }
    val games = lines.map { game ->
        val parts = game.split(":")
        val turns = parts[1].split(";")
        Game(parts[0].replace("Game","").trim().toInt(), turns.map { turn ->
            val ballsInATurn = turn.split(",").map { it.trim() }
            val turns: List<Pair<Int, Balls>> = ballsInATurn.flatMap { balls ->
                balls.split(' ').zipWithNext().map { it.first.toInt() to Balls.valueOf(it.second) }
            }
            val redSum = turns.filter { it.second == Balls.red }.sumOf { it.first }
            val greenSum = turns.filter { it.second == Balls.green }.sumOf { it.first }
            val blueSum = turns.filter { it.second == Balls.blue }.sumOf { it.first }
            Turn(redSum, greenSum, blueSum)
        })
    }
    println(games.filter(Game::valid).sumOf { it.id })
}
