import java.lang.RuntimeException

fun main() {
    fun part1(numbers: List<Int>, boards: List<Board>): Int {
        for (n in numbers){
            for (b in boards){
                if (b.markNum(n)){
                    return b.getScore() * n
                }
            }
        }
        throw RuntimeException("No Solution")
    }

    fun part2(numbers: List<Int>, boards: List<Board>): Int {
        val finishedBoards = boards.map { _ -> false }.toBooleanArray()
        for (n in numbers){
            for ((i,b) in boards.withIndex()){
                if (!finishedBoards[i] && b.markNum(n)){
                    finishedBoards[i] = true
                    if (finishedBoards.all { it }){
                        return b.getScore() * n
                }
            }
        }
        }
        throw RuntimeException("No Solution")
    }

    val input = readInput("day4")
    val (numbers,boards) = parseBingo(input)
    println(part1(numbers, boards))
    println(part2(numbers, boards))
}


fun parseBingo(input: List<String>): Pair<List<Int>,List<Board>> {
    val numbers = input.first().split(',').map { Integer.valueOf(it) }
    val boards  = input.subList(1,input.size)// There must be a better way to skip an element ?!
            .chunked(6) { block ->
                Board(block.subList(1,block.size).map { line ->
                    line.trim().split("\\s+".toRegex()).map { Integer.valueOf(it) } })
            }
    return Pair(numbers,boards)
}

class Board(private val board: List<List<Int>>) {
    private val seen: HashSet<Pair<Int,Int>> = HashSet()

    fun markNum(num: Int) :Boolean{
        var ret = false
        for ((y,row) in board.withIndex()) {
            for ((x,n) in row.withIndex()) {
                if (n == num) {
                    seen.add(Pair(x,y))
                    if (seen.filter { (_,r) -> r == y }.size == 5 ||
                    seen.filter { (c,_) -> c == x }.size == 5) {
                        ret = true
                    }
                }
            }
        }
        return ret
    }

    fun getScore(): Int {
        var res = 0
        for ((y,row) in board.withIndex()) {
            for ((x, n) in row.withIndex()) {
                if (!seen.contains(Pair(x,y))){
                    res += n
                }
            }
        }
        return  res
    }
}