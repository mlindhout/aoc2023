package day01

val numbers = listOf(
    "one","two","three","four","five","six","seven","eight","nine"
)
val digits = listOf(1,2,3,4,5,6,7,8,9).map(Int::toString)

fun main() {

    val work = lines.split("\n")
    val wordsReplaced = work.map { line ->
        line.recursiveReplaceWordsWithDigits()
    }
    wordsReplaced.map {
        val digits = it.filter { c -> c.isDigit() }
        if (digits.isBlank()) {
            0
        } else {
            val first = digits[0]
            val last = digits.reversed()[0]
            "$first$last".toInt()
        }
    }.sum().also { println(it) }
}

fun String.startsWithOneOf(words: List<String>):Pair<String, Int> = (words.mapIndexed {i,w->w to i} .firstOrNull {
    this.startsWith(it.first)
} ?: "" to -1)

fun String.recursiveReplaceWordsWithDigits(): String {
    fun fun0(work: String, output: String): String {
        return if (work.isEmpty()) {
            output
        } else {
            val startMatch = work.startsWithOneOf(numbers)
            if (startMatch.second >= 0) {

                val oldVal = startMatch.first.take(startMatch.first.length-1).drop(1)
                fun0(work.replaceFirst(oldVal, digits[startMatch.second]),output)
            } else {
                fun0(work.drop(1),output+work[0])
            }
        }
    }
    return fun0(this, "")
}
