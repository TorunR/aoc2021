import java.util.*
import kotlin.math.sqrt
import kotlin.streams.toList

fun main() {

    fun getScore(p: Pair<Int, Int>, end: Pair<Int, Int>): Double {
        return sqrt((end.first - p.first).toDouble() * (end.second - p.second).toDouble())
    }

    fun part1(input: List<List<Int>>, print:Boolean = false): Int {
        val start = Pair(0, 0)
        val end = Pair(input.size - 1, input[0].size - 1)
        val fscore = mapOf(Pair(start, Pair(0, Pair(-1, -1)))).toMutableMap()
        val toVisit = PriorityQueue<Pair<Int, Int>>(compareBy{ fscore[it]!!.first + getScore(it,end)})
        toVisit.add(start)
        while (toVisit.isNotEmpty()) {
            val curr = toVisit.remove()
            val (x, y) = curr
            if (curr == end) {
                break
            }
            for (neighbor in listOf(Pair(x, y + 1), Pair(x, y - 1), Pair(x - 1, y), Pair(x + 1, y))) {
                val (nX, nY) = neighbor
                val dist = try {
                    fscore[curr]!!.first + input[nY][nX]
                } catch (e: IndexOutOfBoundsException) {
                    continue
                }
                if (!fscore.containsKey(neighbor) || fscore[neighbor]!!.first > dist) {
                    fscore[neighbor] = Pair(dist, curr)
                    toVisit.add(neighbor)
                }
            }
        }
        if (print) {
            val path = emptySet<Pair<Int, Int>>().toMutableSet()
            var curr = end
            while (curr != Pair(-1, -1)) {
                path.add(curr)
                curr = fscore[curr]!!.second
            }
            for ((y, row) in input.withIndex()) {
                for ((x, v) in row.withIndex()) {
                    if (path.contains(Pair(x, y))) {
                        print("\u001B[1;31m" + v + "\u001B[0m")
                    } else {
                        print(v)
                    }
                }
                println()
            }
        }
        return fscore[end]!!.first
    }

    fun part2(input: List<List<Int>>): Int {
        val fullGrid = arrayOfNulls<List<Int>>(input.size * 5)
        for ((y, row) in input.withIndex()) {
            for (repeatY in 0 until 5) {
                val newRow = IntArray(row.size * 5)
                for ((x, v) in row.withIndex()) {
                    for (repeatX in 0 until 5) {
                        val newV = v + repeatX + repeatY
                        newRow[x + (repeatX * row.size)] = if (newV < 10) newV else newV - 9
                    }
                }
                fullGrid[y + (repeatY * input.size)] = newRow.toList()
            }
        }
        return part1(fullGrid.toList() as List<List<Int>>)
    }

    val input = readInput("day15").map { line ->
        line.chars().map { Character.getNumericValue(it) }.toList()
    }
    val part1Start = System.currentTimeMillis()
    println(part1(input))
    val part1End = System.currentTimeMillis()
    println("Part 1 took ${part1End - part1Start} ms")
    val part2Start = System.currentTimeMillis()
    println(part2(input))
    val part2End = System.currentTimeMillis()
    println("Part 2 took ${part2End - part2Start} ms")
}


