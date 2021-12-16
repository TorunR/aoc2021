fun main() {
    fun part1(input: List<String>): Int {
        return input.map { Integer.valueOf(it) }.zipWithNext().fold(0) { acc, (first,second) ->
            if (first < second) acc + 1 else acc
        }
    }

    fun part2(input: List<String>): Int {
        return input.map {Integer.valueOf(it) }.windowed(3).zipWithNext().fold(0) { acc, (first,second) ->
            if (first[0] < second[2]) acc + 1 else acc
        }
    }

    val input = readInput("day1")
    println(part1(input))
    println(part2(input))
}
