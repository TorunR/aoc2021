import java.lang.Long.max
import java.lang.Long.min
import java.lang.RuntimeException
import java.math.BigInteger

fun main() {
    fun parseValue(index: Int, input: String): Pair<Long, Int> {
        var cont = true
        var index = index
        val sb = StringBuilder()
        while (cont) {
            cont = input[index] == '1'
            sb.append(input.substring(index + 1, index + 5))
            index += 5
        }
        val value = BigInteger(sb.toString(), 2).toLong()
        return Pair(value, index)
    }

    fun parsePacket(index: Int, input: String): Triple<Int, Long, Int> {
        val version = Integer.valueOf(input.substring(index, index + 3), 2)
        val type = Integer.valueOf(input.substring(index + 3, index + 6), 2)
        var index = index + 6
        when (type) {
            4 -> {
                val (v, newIndex) = parseValue(index, input)
                return Triple(version, v, newIndex)
            }
            else -> {
                var subValues = emptyList<Long>()
                var totalVersion = version
                if (input[index] == '0') {
                    val subPacketLen = Integer.valueOf(input.substring(index + 1, index + 16), 2)
                    index += 16
                    val targetIndex = index + subPacketLen
                    while (index != targetIndex) {
                        val (subVersion, v, newIndex) = parsePacket(index, input)
                        index = newIndex
                        totalVersion += subVersion
                        subValues += v
                    }

                } else {
                    val subPacketNum = Integer.valueOf(input.substring(index + 1, index + 12), 2)
                    index += 12
                    for (i in 0 until subPacketNum) {
                        val (subVersion, v, newIndex) = parsePacket(index, input)
                        index = newIndex
                        totalVersion += subVersion
                        subValues += v
                    }
                }
                val res = when (type) {
                    0 -> subValues.sum()// sum
                    1 -> subValues.fold(1L) { v1, v2 -> v1 * v2 }
                    2 -> subValues.fold(Long.MAX_VALUE) { v1, v2 -> min(v1, v2) }
                    3 -> subValues.fold(Long.MIN_VALUE) { v1, v2 -> max(v1, v2) }
                    5 -> if (subValues[0] > subValues[1]) 1L else 0L
                    6 -> if (subValues[0] < subValues[1]) 1L else 0L
                    7 -> if (subValues[0] == subValues[1]) 1L else 0L
                    else -> throw IllegalArgumentException("Unknown op")
                }
                return Triple(totalVersion, res, index)
            }
        }
    }


    fun part1and2(input: String): Pair<Int, Long> {
        val (version, v, newIndex) = parsePacket(0, input)
        return Pair(version, v)
    }

    var input = BigInteger(readInput("day16")[0], 16).toString(2)
    val len = (4 - input.length % 4) % 4 // Frontpad to half bytes
    input = "0".repeat(len) + input
    val part1Start = System.currentTimeMillis()
    println(part1and2(input))
    val part1End = System.currentTimeMillis()
    println("All took ${part1End - part1Start} ms")
}


