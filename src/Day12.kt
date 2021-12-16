import java.lang.Exception
import kotlin.streams.toList

fun main() {
    fun buildMap(input: List<Pair<String,String>>): Map<String,List<String>> {
        val map = emptyMap<String,List<String>>().toMutableMap().withDefault { emptyList() }
        input.forEach{(from,to) ->
            map[from] = map.getValue(from).plus(to)
            map[to] = map.getValue(to).plus(from)
        }
        return map.toMap()
    }
    fun isBig(cave: String):Boolean {
       return cave.uppercase() == cave
    }

    fun buildPath(curr:String,curPath:List<String>,cave: Map<String,List<String>>, visited: Set<String>):List<List<String>> {
        return cave[curr]?.flatMap { nextCave ->
            if (curr != "end" && (isBig(nextCave) || !visited.contains(nextCave))){
                buildPath(nextCave,curPath + curr, cave, visited + curr)
            } else {
                listOf(curPath + curr)
            }
        } ?: emptyList()
    }
    fun buildPath2(curr:String,curPath:List<String>,
                   cave: Map<String,List<String>>, visited: Set<String>,visitedTwice: Boolean):List<List<String>> {
        return cave[curr]?.flatMap { nextCave ->
            if (curr == "end") {
                listOf(curPath + curr)
            } else if ((isBig(nextCave) || !visited.contains(nextCave) && nextCave != "start")){
                if (visitedTwice) {
                    buildPath2(nextCave, curPath + curr, cave, visited + curr,true)
                } else {
                    buildPath2(nextCave, curPath + curr, cave, visited,true) +
                    buildPath2(nextCave, curPath + curr, cave, visited + curr,false)
                }
            } else {
                listOf(curPath + curr)
            }
        } ?: emptyList()
    }

    fun part1(input: List<Pair<String,String>>): Int {
        val paths = buildMap(input)
        val res = buildPath("start", emptyList(),paths, emptySet()).toSet().filter { it.last() == "end" }
        return res.size
    }

    fun part2(input: List<Pair<String,String>>): Int {
        val paths = buildMap(input)
        val res = buildPath2("start", emptyList(),paths, emptySet(),false).toSet().filter { it.last() == "end" }
        return res.size
    }

    val input = readInput("day12").map { line ->
        val split = line.split('-')
        Pair(split[0],split[1])
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


