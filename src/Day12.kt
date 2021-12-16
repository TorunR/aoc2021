fun main() {

    fun buildMap(input: List<Pair<String,String>>): Map<String,List<String>> {
        val map = emptyMap<String,List<String>>().toMutableMap().withDefault { emptyList() }
        input.forEach{(from,to) ->
            map[from] = map.getValue(from).plus(to)
            map[to] = map.getValue(to).plus(from)
        }
        return map.toMap()
    }

    fun buildPath(curr:String, curPath:List<String>,
                  cave: Map<String,List<String>>, visited: Set<String>, visitedTwice: Boolean):List<List<String>> {
        return if (curr == "end") {
            listOf(curPath + curr)
        } else cave[curr]?.flatMap { nextCave ->
            val isBig = nextCave[0].isUpperCase()
            if (isBig || (!visited.contains(nextCave))){
                if (visitedTwice || isBig) {
                    buildPath(nextCave, curPath + curr, cave, visited + nextCave, visitedTwice)
                } else {
                    (buildPath(nextCave, curPath + curr, cave, visited,true) +
                    buildPath(nextCave, curPath + curr, cave, visited + nextCave,false)).distinct()
                }
            } else {
                emptyList()
            }
        }?.filter { it.isNotEmpty() }?: emptyList()
    }

    fun part1(paths: Map<String,List<String>>): Int {
        val res = buildPath("start", emptyList(),paths, setOf("start"),true)
        return res.size
    }

    fun part2(paths: Map<String,List<String>>): Int {
        val res = buildPath("start", emptyList(),paths, setOf("start"),false)
        return res.size
    }

    val input = buildMap(readInput("day12").map { line ->
        val split = line.split('-')
        Pair(split[0],split[1])
    })

    val part1Start = System.currentTimeMillis()
    println(part1(input))
    val part1End = System.currentTimeMillis()
    println("Part 1 took ${part1End - part1Start} ms")
    val part2Start = System.currentTimeMillis()
    println(part2(input))
    val part2End = System.currentTimeMillis()
    println("Part 2 took ${part2End - part2Start} ms")
}


