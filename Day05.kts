import java.io.File

fun getCommand(value: Int): Pair<Int, String> {
  val cmd = value.toString()
  val size = cmd.length
  val modeSize = size - 2

  if (size == 1) return Pair(value, "0".repeat(2))

  val opCode = cmd.substring(modeSize, size).toInt()
  val definedModes = cmd.substring(0, modeSize)
  var fill = "0".repeat(2 - modeSize)

  return Pair(opCode, fill + definedModes)
}

fun getValue(value: Int, mode: Char, program: MutableList<Int>): Int
  = if (mode == '1') value else program.get(value)

fun add(cursor: Int, mode: String, program: MutableList<Int>): MutableList<Int> {
  val x = getValue(program.get(cursor + 1), mode.get(1), program)
  val y = getValue(program.get(cursor + 2), mode.get(0), program)
  val res = x + y
  val addr = program.get(cursor + 3)
  program.set(addr, res)
  return program
}

fun mul(cursor: Int, mode: String, program: MutableList<Int>): MutableList<Int> {
  val x = getValue(program.get(cursor + 1), mode.get(1), program)
  val y = getValue(program.get(cursor + 2), mode.get(0), program)
  val res = x * y
  val addr = program.get(cursor + 3)
  program.set(addr, res)
  return program
}

fun set(input: Int, cursor: Int, program: MutableList<Int>): MutableList<Int> {
  val addr = program.get(cursor + 1)
  program.set(addr, input)
  return program
}

fun dbg(cursor: Int, mode: String, program: MutableList<Int>): MutableList<Int> {
  val x = getValue(program.get(cursor + 1), mode.get(1), program)
  println(x)
  return program
}

fun nz(cursor: Int, mode: String, program: MutableList<Int>): Int {
  val x = getValue(program.get(cursor + 1), mode.get(1), program)
  val addr = getValue(program.get(cursor + 2), mode.get(0), program)
  return if (x != 0) addr else cursor + 3
}

fun iz(cursor: Int, mode: String, program: MutableList<Int>): Int {
  val x = getValue(program.get(cursor + 1), mode.get(1), program)
  val addr = getValue(program.get(cursor + 2), mode.get(0), program)
  return if (x == 0) addr else cursor + 3
}

fun lt(cursor: Int, mode: String, program: MutableList<Int>): MutableList<Int> {
  val x = getValue(program.get(cursor + 1), mode.get(1), program)
  val y = getValue(program.get(cursor + 2), mode.get(0), program)
  val addr = program.get(cursor + 3)
  program.set(addr, if (x < y ) 1 else 0)
  return program
}

fun eq(cursor: Int, mode: String, program: MutableList<Int>): MutableList<Int> {
  val x = getValue(program.get(cursor + 1), mode.get(1), program)
  val y = getValue(program.get(cursor + 2), mode.get(0), program)
  val addr = program.get(cursor + 3)
  program.set(addr, if (x == y ) 1 else 0)
  return program
}

fun computer(program: MutableList<Int>, cursor: Int = 0, input: Int = 5) {
  val (opCode, mode) = getCommand(program.get(cursor))
  when (opCode) {
    99 -> return
    1 -> return computer(add(cursor, mode, program), cursor + 4)
    2 -> return computer(mul(cursor, mode, program), cursor + 4)
    3 -> return computer(set(input, cursor, program), cursor + 2)
    4 -> return computer(dbg(cursor, mode, program), cursor + 2)
    5 -> return computer(program, nz(cursor, mode, program))
    6 -> return computer(program, iz(cursor, mode, program))
    7 -> return computer(lt(cursor, mode, program), cursor + 4)
    8 -> return computer(eq(cursor, mode, program), cursor + 4)
    else -> throw Error("Shit went ham!")
  }
}

fun readFile(filename: String): MutableList<Int>
  = File(filename).useLines() { it.first().split(",") }.map{ it.toInt() }.toMutableList()

val inputs = readFile("./data/day05.txt")

computer(inputs) // A: 15259545 | B: 7616021
