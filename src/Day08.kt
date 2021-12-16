

fun main() {
    fun part1(input: List<SegmentPattern>): Int {
        return input.fold(0) { akk, pattern ->
            pattern.output.filter { it.length == 2 || it.length == 4 || it.length == 3 || it.length == 7 }.size +
                    akk
        }
    }

    fun part2(input: List<SegmentPattern>): Int {
        return input.map { it.decode() }.sum()
    }

    val input = readInput("day8").map { line ->
        val split = line.split(" | ")
        val display = split[0].split(' ')
        val output = split[1].split(' ')
        SegmentPattern(display, output)
    }
    val part1Start = System.currentTimeMillis()
    println(part1(input))
    val part1End = System.currentTimeMillis()
    println("Part1 took ${part1End - part1Start} ms")
    val part2Start = System.currentTimeMillis()
    println(part2(input))
    val part2End = System.currentTimeMillis()
    println("Part2 took ${part2End - part2Start} ms")

}

data class SegmentPattern(val display:List<String>,val output: List<String>) {

    fun decode():Int {
        val sorted= display.map { it.toSortedSet() }.sortedBy { it.size }
        val s1 = sorted[0]
        val s4 = sorted[2]
        val s7 = sorted[1]
        val s8 = sorted[9]
        val a = s7.subtract(s1) // 7 - 1
        val s9 = sorted.filter { it.size == 6 }.filter { it.intersect(s4.plus(a)).size == 5}[0] // 9 = 4 + a
        val g = s9.subtract(s4.plus(a))
        val e = s8.subtract(s9)
        val s6 = sorted.filter { it.size == 6 && it != s9 }.filter { it.intersect(s1).size == 1 }[0]
        val s0 = sorted.filter { it.size == 6 && it != s9 }.filter { it.intersect(s1).size == 2 }[0]
        val s5 = sorted.filter { it.size == 5}.filter { it.intersect(s6).size == 5 }[0]
        val s3 = sorted.filter { it.size == 5 && it != s5}.filter { it.intersect(s9).size == 5 }[0]
        val s2 = sorted.filter { it.size == 5 && it != s5 && it != s3}[0]
        val b = ""
        val c = s8.subtract(s6)
        val d = s8.subtract(s0)
        val f = ""
        val decoder = mapOf(
            s0 to "0",
            s1 to "1",
            s2 to "2",
            s3 to "3",
            s4 to "4",
            s5 to "5",
            s6 to "6",
            s7 to "7",
            s8 to "8",
            s9 to "9"
        )
        val res = output.map { it.toSortedSet() }.map { decoder.get(it)!! }.joinToString(separator = "")
        return Integer.valueOf(res)
    }
}