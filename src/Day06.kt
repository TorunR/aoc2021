fun main() {

    fun simFish(input: List<Int>, days: Int):Long {
        var fish = Array(9) { _ -> 0L }
        var numFish = input.size.toLong()
        input.forEach {
            fish[it]++
        }
        for (day in 0 until  days) {
            val newFish = Array(9) { _ -> 0L }
            newFish[6] = fish[0]
            newFish[8] = fish[0]
            numFish += fish[0]
            for (i in 1 .. 8) {
                newFish[i-1] += fish[i]
            }
            fish = newFish
        }
        return numFish
    }

    fun part1(input: List<Int>): Long {
        return simFish(input,80)
    }

    fun part2(input: List<Int>): Long {
      return simFish(input, 256)
    }

    val input = readInput("day6")[0].split(',').map {Integer.valueOf(it) }
    println(part1(input))
    println(part2(input))
}
