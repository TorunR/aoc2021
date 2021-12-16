fun main() {

    fun part1and2(template: String, rules: Map<String, String>, steps:Int = 10): Long {
        var pairs = emptyMap<String,Long>().toMutableMap()
        template.windowed(2).forEach { pairs[it] = 1L + (pairs[it]?: 0L) }
        for (step in 0 until steps) {
            val newPairs = emptyMap<String,Long>().toMutableMap()
            for ((pair,count) in pairs) {
                val newElement = rules[pair]!!
                val pair1 = pair[0] + newElement
                val pair2 = newElement + pair[1]
                newPairs[pair1] = count + (newPairs[pair1] ?: 0L)
                newPairs[pair2] = count + (newPairs[pair2] ?: 0L)
            }
            pairs = newPairs
        }
        val elementMap = emptyMap<Char,Long>().toMutableMap()
        // Off by one for the first char, because we only add the second element of all pairs
        // so add it manually
        elementMap[template[0]] = 1
        for ((pair,count) in  pairs) {
            elementMap[pair[1]] = count + (elementMap[pair[1]]?: 0)
        }
        val max = elementMap.values.maxOrNull()!!
        val min = elementMap.values.minOrNull()!!
        return max - min
    }

    val input = readInput("day14")
    val template = input[0]
    val rules = input.subList(2, input.size).map {
        val s = it.split(" -> ")
        Pair(s[0], s[1])
    }.toMap()
    val part1Start = System.currentTimeMillis()
    println(part1and2(template, rules,10))
    val part1End = System.currentTimeMillis()
    println("Part 1 took ${part1End - part1Start} ms")
    val part2Start = System.currentTimeMillis()
    println(part1and2(template, rules,40))
    val part2End = System.currentTimeMillis()
    println("Part 2 took ${part2End - part2Start} ms")
}


