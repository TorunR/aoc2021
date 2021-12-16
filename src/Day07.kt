import java.lang.Integer.max
import java.lang.Integer.min

fun main() {
    fun part1(input: List<Int>): Int {
        val max = input.maxOrNull()!!
        val min = input.minOrNull()!!
        val distances = IntArray(max - min)
        for ((i,_) in distances.withIndex()) {
            distances[i] = input.fold(0) { akk, crab ->
                akk + max(crab,i) - min(crab,i)
            }
        }
        return distances.minOrNull()!!
    }

    fun part2(input: List<Int>): Int {
        val max = input.maxOrNull()!!
        val min = input.minOrNull()!!
        val distances = IntArray(max - min)
        for ((i,_) in distances.withIndex()) {
            distances[i] = input.fold(0) { akk, crab ->
                val toTravel = max(crab,i) - min(crab,i)
                akk + ((toTravel * (toTravel +1 )) / 2)
            }
        }
        return distances.minOrNull()!!
    }

    val input = readInput("day7")[0].split(',').map { Integer.valueOf(it) }
    val part1Start = System.currentTimeMillis()
    println(part1(input))
    val part1End = System.currentTimeMillis()
    println("Part1 took ${part1End - part1Start} ms")
    val part2Start = System.currentTimeMillis()
    println(part2(input))
    val part2End = System.currentTimeMillis()
    println("Part2 took ${part2End - part2Start} ms")

}
