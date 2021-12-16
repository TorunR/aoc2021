import kotlin.streams.toList

fun main() {
    fun part1(input: List<List<Int>>): Int {
        var risk = 0
        for (y in 0 until input.size) {
            for (x in 0 until input[y].size){
                val above  = try {input[y-1][x]} catch (e:IndexOutOfBoundsException) { Int.MAX_VALUE }
                val below = try {input[y+1][x]} catch (e:IndexOutOfBoundsException) { Int.MAX_VALUE }
                val left = try {input[y][x-1]} catch (e:IndexOutOfBoundsException) { Int.MAX_VALUE }
                val right = try {input[y][x+1]} catch (e:IndexOutOfBoundsException) { Int.MAX_VALUE }
                val element = input[y][x]
                if (element < above && element < below && element < left && element < right) {
                    risk += element + 1
                }
            }
        }
        return risk
    }

    fun part2(input: List<List<Int>>): Int {
        val basins = emptyList<List<Pair<Int,Int>>>().toMutableList()
        val lowPoints = emptyList<Pair<Int,Int>>().toMutableList()
        for (y in 0 until input.size) {
            for (x in 0 until input[y].size){
                val above  = try {input[y-1][x]} catch (e:IndexOutOfBoundsException) { Int.MAX_VALUE }
                val below = try {input[y+1][x]} catch (e:IndexOutOfBoundsException) { Int.MAX_VALUE }
                val left = try {input[y][x-1]} catch (e:IndexOutOfBoundsException) { Int.MAX_VALUE }
                val right = try {input[y][x+1]} catch (e:IndexOutOfBoundsException) { Int.MAX_VALUE }
                val element = input[y][x]
                if (element < above && element < below && element < left && element < right) {
                    lowPoints += Pair(x,y)
                }
            }
        }
        for (point in lowPoints){
            val toVisit = ArrayDeque<Pair<Int,Int>>()
            toVisit+= point
            val basin = emptyList<Pair<Int,Int>>().toMutableList()
            val seen = emptySet<Pair<Int,Int>>().toMutableSet()
            while (toVisit.isNotEmpty()) {
                val curr = toVisit.removeFirst()
                if (!seen.contains(curr)){
                    seen += curr
                    val (x,y) = curr
                    val cont = try {input[y][x]} catch (e:IndexOutOfBoundsException) { 9 }
                    if (cont != 9) {
                        basin += Pair(x,y)
                        toVisit.add(Pair(x,y+1))
                        toVisit.add(Pair(x,y-1))
                        toVisit.add(Pair(x+1,y))
                        toVisit.add(Pair(x-1,y))
                    }
                }
            }
            basins += basin.toList()
        }
        return basins.sortedByDescending { it.size }.take(3).map { it.size }.fold(1){ akk,el -> akk * el}
    }

    val input = readInput("day9").map { line ->
        line.chars().map {  Character.getNumericValue(it) }.toList()
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


