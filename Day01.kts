import java.io.File

fun calcFuel(value: Int): Int
  = (value / 3) - 2

fun calcFuelRec(value: Int): Int
  = calcFuel(value).let { if (it <= 0) 0 else it + calcFuelRec(it) }

fun solve(inputs: List<Int>, func: (value: Int) -> Int): Int
  = inputs.fold(0) { acc, it -> acc + func(it) }

fun readFile(filename: String): List<Int>
  = File(filename)
    .readLines()
    .map { it.toInt() }

val inputs = readFile("./data/day01.txt")
println("Part One: " + solve(inputs, ::calcFuel)) // => 3399394
println("Part Two: " + solve(inputs, ::calcFuelRec)) // => 5096223
