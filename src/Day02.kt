fun main() {
    fun part1(input: List<Pair<String, Int>>): Int {
        val (depth,horizontal)  = input.fold(Pair(0,0)) { (depth,horizontal),(cmd,param) ->
            when (cmd) {
                "forward" -> Pair(depth,horizontal + param)
                "down"    -> Pair(depth + param, horizontal)
                "up" -> Pair(depth - param, horizontal)
                else ->
                    throw IllegalArgumentException("Unknown Command")
            }
        }
        return depth * horizontal
    }

    fun part2(input: List<Pair<String, Int>>): Int {
        val (depth,horizontal,_)  = input.fold(Triple(0,0,0)) { (depth,horizontal,aim), (cmd,param) ->
            when (cmd) {
                "forward" -> Triple(depth + (param * aim),horizontal + param, aim)
                "down"    -> Triple(depth, horizontal, aim + param)
                "up" -> Triple(depth, horizontal, aim - param)
                else ->
                    throw IllegalArgumentException("Unknown Command")
            }
        }
        return depth * horizontal
    }

    // test if implementation meets criteria from the description, like:
    val input = readInput("day2").map { line ->
        val split = line.split(" ")
        Pair(split[0], Integer.valueOf(split[1]))
    }

    println(part1(input))
    println(part2(input))
}

