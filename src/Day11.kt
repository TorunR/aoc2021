import java.lang.Exception
import kotlin.streams.toList

fun main() {
    fun flash(x: Int, y: Int, grid: List<MutableList<Int>>, flashed: MutableSet<Pair<Int, Int>>) {

            try {
                grid[y][x]+= 1
                if (!flashed.contains(Pair(x, y))) {
                if (grid[y][x] > 9) {
                    flashed += Pair(x, y)
                    flash(x - 1, y - 1, grid, flashed)
                    flash(x - 1, y, grid, flashed)
                    flash(x - 1, y + 1, grid, flashed)
                    flash(x, y - 1, grid, flashed)
                    flash(x, y + 1, grid, flashed)
                    flash(x + 1, y - 1, grid, flashed)
                    flash(x + 1, y, grid, flashed)
                    flash(x + 1, y + 1, grid, flashed)
                }}
            } catch (e: IndexOutOfBoundsException) {
            }
            print("")

    }

    fun part1(input: List<List<Int>>): Int {
        val grid = input.map { it.toMutableList() }
        var flashes = 0
        for (step in 0 until 100) {
            var flashed = emptySet<Pair<Int, Int>>().toMutableSet()
            for (y in 0 until input.size) {
                for (x in 0 until input[y].size) {
                    grid[y][x] += 1
                }
            }
            for (y in 0 until input.size) {
                for (x in 0 until input[y].size) {
                    if (grid[y][x] > 9) {
                        flash(x, y, grid, flashed)
                    }
                }
            }
            for (y in 0 until input.size) {
                for (x in 0 until input[y].size) {
                    if (grid[y][x] > 9) {
                        grid[y][x] = 0
                    }
                }

            }
            flashes += flashed.size
        }
        return flashes
    }

    fun part2(input: List<List<Int>>): Int {
        val grid = input.map { it.toMutableList() }
        var steps = 0
        while (!grid.all { it.all { it == 0 } }){
            var flashed = emptySet<Pair<Int, Int>>().toMutableSet()
            for (y in 0 until input.size) {
                for (x in 0 until input[y].size) {
                    grid[y][x] += 1
                }
            }
            for (y in 0 until input.size) {
                for (x in 0 until input[y].size) {
                    if (grid[y][x] > 9) {
                        flash(x, y, grid, flashed)
                    }
                }
            }
            for (y in 0 until input.size) {
                for (x in 0 until input[y].size) {
                    if (grid[y][x] > 9) {
                        grid[y][x] = 0
                    }
                }
            }
            steps+=1
        }
        return steps
    }

    val input = readInput("day11").map { line ->
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


