import java.io.File
import kotlin.system.exitProcess

fun mul(x: Int, y: Int): Int
  = x * y
 
fun add(x: Int, y: Int): Int
  = x + y

fun getValue(op: List<Int>, program: List<Int>, func: (x: Int, y: Int) -> Int): Int
  = func(program.get(op.get(1)), program.get(op.get(2)))

fun setValue(value: Int, pos: Int, program: List<Int>): List<Int> {
  val newList = program.toMutableList()
  newList.set(pos, value)
  return newList
}

fun computer(program: List<Int>, pos: Int = 0): Int {
  val op = program.subList(pos, program.size)
  when(op.get(0)) {
    99 -> return program.get(0)
    1 -> return computer(setValue(getValue(op, program, ::add), op.get(3), program), pos + 4)
    2 -> return computer(setValue(getValue(op, program, ::mul), op.get(3), program), pos + 4)
    else -> throw Error("SHIT WENT HAM!!")
  }
}

fun readFile(filename: String): MutableList<Int>
  = File(filename).useLines() { it.first().split(",") }.map{ it.toInt() }.toMutableList()

val inputs = readFile("./data/dayTwo.txt")

val partA = inputs.toMutableList().also { it.set(1, 12) }.also { it.set(2, 2) }
println("Part One: " + computer(partA.toList())) // => 4090701

for ( noun in 0..99) {
  for ( verb in 0..99) {
    val partB = inputs.toMutableList().also { it.set(1, noun) }.also { it.set(2, verb) }
    val res = computer(partB.toList())

    if (res == 19690720) {
      println("Part Two: " + (100 * noun + verb)) // => 6421
      exitProcess(0)
    }
  }
}
