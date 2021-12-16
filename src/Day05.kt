import kotlin.math.max
import kotlin.math.min

fun main() {
    fun part1(input: List<Line>): Int {
        val grid = HashMap<Pair<Int, Int>, Int>().withDefault { _ -> 0 }
        input.filter { it.isStraight()}.forEach{it.addToGrid(grid)}
        return grid.values.filter { it >= 2 }.size
    }

    fun part2(input: List<Line>): Int {
        val grid = HashMap<Pair<Int, Int>, Int>().withDefault { _ -> 0 }
        input.forEach{it.addToGrid(grid)}
        return grid.values.filter { it >= 2 }.size
    }

    val input = readInput("day5")
    val lines = input.map { Line.fromLine(it) }
    println(part1(lines))
    println(part2(lines))
}

data class Line(val x1:Int,val y1:Int,val x2:Int,val y2:Int) {
    companion object{
        private val re = "(?<x1>\\d+),(?<y1>\\d+) -> (?<x2>\\d+),(?<y2>\\d+)".toRegex()
        fun fromLine(line:String) : Line {
            val match = re.matchEntire(line)!!
            val x1 = Integer.valueOf(match.groups["x1"]?.value)
            val y1 = Integer.valueOf(match.groups["y1"]?.value)
            val x2 = Integer.valueOf(match.groups["x2"]?.value)
            val y2 = Integer.valueOf(match.groups["y2"]?.value)
            return Line(x1,y1, x2, y2)
        }
    }

    fun isStraight():Boolean {
        return x1 == x2 || y1 == y2
    }

    fun addToGrid(grid: MutableMap<Pair<Int, Int>, Int>) {
        if (x1 == x2) {
            for (i in min(y1, y2)..max(y1, y2)) {
                grid.compute(Pair(x1, i)) { (_, _), old ->
                    1 + (old ?: 0)
                }
            }
        } else if (y1 == y2) {
            for (i in min(x1, x2)..max(x1, x2)) {
                grid.compute(Pair(i, y1)) { (_, _), old ->
                    1 + (old ?: 0)
                }
            }
        } else {
            val xDir = if (x1 < x2) 1 else -1
            val yDir = if (y1 < y2) 1 else -1
            var x = x1
            var y = y1
            for (i in 0 .. (max(x1,x2) - min(x1,x2))) {
                grid.compute(Pair(x, y)) { (_, _), old ->
                    1 + (old ?: 0)
                }
                x += xDir
                y += yDir
            }
        }
    }
}