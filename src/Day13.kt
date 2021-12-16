fun main() {

    fun part1and2(input: List<Pair<Int, Int>>, folds: List<Pair<String, Int>>, print: Boolean = false): Int {
        var coords = input.toMutableSet()
        for ((dir, axis) in folds) {
            if (dir == "x") {
                coords.filter { (x, y) -> x > axis }.forEach { p ->
                    val (x, y) = p
                    val dist = x - axis
                    coords.remove(p)
                    coords.add(Pair(axis - dist, y))
                }
            } else { // assume y
                coords.filter { (x, y) -> y > axis }.forEach { p ->
                    val (x, y) = p
                    val dist = y - axis
                    coords.remove(p)
                    coords.add(Pair(x, axis - dist))
                }
            }
        }
        if (print) {
            for (y in 0..coords.maxOf { it.second }) {
                for (x in 0..coords.maxOf { it.first }) {
                    if (coords.contains(Pair(x, y))) {
                        print('#')
                    } else {
                        print(' ')
                    }
                }
                println()

            }
        }
        return coords.size
    }

    val input = readInput("day13")
    val coords = input.takeWhile { it.isNotEmpty() }.map { line ->
        val split = line.split(',').map { Integer.valueOf(it) }
        Pair(split[0], split[1])
    }
    val folds = input.takeLastWhile { it.isNotEmpty() }.map { line ->
        val split = line.split(' ')[2].split('=')
        Pair(split[0], Integer.valueOf(split[1]))
    }
    val part1Start = System.currentTimeMillis()
    println(part1and2(coords, folds.take(1)))
    val part1End = System.currentTimeMillis()
    println("Part 1 took ${part1End - part1Start} ms")
    val part2Start = System.currentTimeMillis()
    println(part1and2(coords, folds, true))
    val part2End = System.currentTimeMillis()
    println("Part 2 took ${part2End - part2Start} ms")
}


