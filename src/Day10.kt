fun main() {
    fun getScoreCorrupt(sym: Char): Int {
        return when (sym){
            ')' -> 3
            ']' -> 57
            '}' -> 1197
            '>' -> 25137
            else -> throw IllegalArgumentException()
        }
    }

    fun part1(input: List<String>): Int {
        var score = 0;
        for (line in input) {
            val stack = ArrayDeque<Char>()
            for (sym in line) {
                val last = stack.lastOrNull()
                when (sym) {
                    '(' , '[' , '{' , '<' -> stack.addLast(sym)
                    ')' -> if (last != '(') {score += getScoreCorrupt(sym);break} else {stack.removeLast()}
                    ']' -> if (last != '[') {score += getScoreCorrupt(sym);break} else {stack.removeLast()}
                    '}' -> if (last != '{') {score += getScoreCorrupt(sym);break} else {stack.removeLast()}
                    '>' -> if (last != '<') {score += getScoreCorrupt(sym);break} else {stack.removeLast()}
                }
            }
        }
        return score
    }

    fun part2(input: List<String>): Long {
        val scores = emptyList<Long>().toMutableList()
        for (line in input) {
            val stack = ArrayDeque<Char>()
            for (sym in line) {
                val last = stack.lastOrNull()
                when (sym) {
                    '(' , '[' , '{' , '<' -> stack.addLast(sym)
                    ')' -> if (last != '(') {stack.clear();break} else {stack.removeLast()}
                    ']' -> if (last != '[') {stack.clear();break} else {stack.removeLast()}
                    '}' -> if (last != '{') {stack.clear();break} else {stack.removeLast()}
                    '>' -> if (last != '<') {stack.clear();break} else {stack.removeLast()}
                }
            }
            var score = 0L
            while (stack.isNotEmpty()) {
                val curr = stack.removeLast()
                score *= 5
                score += when (curr) {
                    '(' -> 1
                    '[' -> 2
                    '{' -> 3
                    '<' -> 4
                    else -> throw IllegalArgumentException()
                }
            }
            scores.add(score)
        }
        val sorted = scores.filter { it != 0L }.sorted()
        return sorted[(sorted.size/2)]
    }


    val input = readInput("day10")
    val part1Start = System.currentTimeMillis()
    println(part1(input))
    val part1End = System.currentTimeMillis()
    println("Part 1 took ${part1End - part1Start} ms")
    val part2Start = System.currentTimeMillis()
    println(part2(input))
    val part2End = System.currentTimeMillis()
    println("Part 2 took ${part2End - part2Start} ms")
}


