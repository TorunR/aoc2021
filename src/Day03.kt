fun main() {
    fun part1(input: List<String>): Int {
       val (gamma,epsilon) = input.fold(Array(input[0].length) { _ -> 0 }) { acc, s ->
           for ((i, char) in s.chars().iterator().withIndex()) {
                if (char == '1'.code) acc[i]+=1
           }
           acc
       }.map { count ->
           if (count > input.size/2) Pair("1", "0") else Pair("0", "1")
       }.unzip()
       return Integer.parseInt(gamma.joinToString(separator = ""), 2) * Integer.parseInt(epsilon.joinToString(separator = ""), 2)
    }

    fun part2(input: List<String>): Int {
        val root = Node(null, null, 0)
        input.forEach { s -> insert(root, s) }
        val oxygen = findValue(root) {a,b -> a > b}
        val co2 = findValue(root) {a,b -> a <= b}
        return Integer.parseInt(oxygen,2) * Integer.parseInt(co2,2)
    }

    // test if implementation meets criteria from the description, like:
    val input = readInput("day3")
    println(part1(input))
    println(part2(input))
}

data class Node(var left: Node?,var right: Node?, var height: Int)

fun insert(root:Node, word:String){
    var curr = root
    word.chars().forEach { bit ->
        curr.height += 1
        if (bit == '0'.code) {
            if (curr.left == null) {
                curr.left = Node(null, null, 0)
            }
            curr = curr.left!!
        } else {
            if (curr.right == null) {
                curr.right = Node(null, null, 0)
            }
            curr = curr.right!!
        }
    }
}

fun findValue(root:Node, compare: (Int,Int) -> Boolean): String {
    val sb = StringBuilder()
    var curr: Node? = root
    while (curr != null && curr.height != 0){
      if (curr.left != null && curr.right != null) {
        if (compare(curr.left?.height ?: -1,curr.right?.height?: -1)){
            curr = curr.left
            sb.append('0')
        } else {
            curr = curr.right
            sb.append('1')
        }
        } else
            if (curr.left == null){
            curr = curr.right
            sb.append('1')
        } else  {
            curr = curr.left
            sb.append('0')
        }
    }
    return sb.toString()
}

