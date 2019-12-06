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
  println("ADD: $x + $y = $res -> $addr")
  return program
}

fun mul(cursor: Int, mode: String, program: MutableList<Int>): MutableList<Int> {
  val x = getValue(program.get(cursor + 1), mode.get(1), program)
  val y = getValue(program.get(cursor + 2), mode.get(0), program)
  val res = x * y
  val addr = program.get(cursor + 3)
  program.set(addr, res)
  println("MUL: $x + $y = $res -> $addr")
  return program
}

fun set(input: Int, cursor: Int, program: MutableList<Int>): MutableList<Int> {
  val addr = program.get(cursor + 1)
  program.set(addr, input)
  println("SET: $input -> $addr")
  return program
}

fun dbg(cursor: Int, mode: String, program: MutableList<Int>): MutableList<Int> {
  val x = getValue(program.get(cursor + 1), mode.get(1), program)
  println("DEBUG: $x")
  return program
}

fun nz(cursor: Int, mode: String, program: MutableList<Int>): Int {
  val x = getValue(program.get(cursor + 1), mode.get(1), program)
  val addr = program.get(cursor + 2)
  println("NZR: $x -> $addr")
  return if (x != 0) addr else cursor + 3
}

fun iz(cursor: Int, mode: String, program: MutableList<Int>): Int {
  val x = getValue(program.get(cursor + 1), mode.get(1), program)
  val addr = program.get(cursor + 2)
  println("ZRO: $x -> $addr")
  return if (x == 0) addr else cursor + 3
}

fun lt(cursor: Int, mode: String, program: MutableList<Int>): MutableList<Int> {
  val x = getValue(program.get(cursor + 1), mode.get(1), program)
  val y = getValue(program.get(cursor + 2), mode.get(0), program)
  val addr = program.get(cursor + 3)
  println("LSS: $x < $y -> $addr")
  program.set(addr, if (x < y ) 1 else 0)
  return program
}

fun eq(cursor: Int, mode: String, program: MutableList<Int>): MutableList<Int> {
  val x = getValue(program.get(cursor + 1), mode.get(1), program)
  val y = getValue(program.get(cursor + 2), mode.get(0), program)
  val addr = program.get(cursor + 3)
  println("EQL: $x == $y -> $addr")
  program.set(addr, if (x == y ) 1 else 0)
  return program
}

fun computer(program: MutableList<Int>, cursor: Int = 0, input: Int = 5) {
  val (opCode, mode) = getCommand(program.get(cursor))
  println(program)
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

computer(inputs)

// computer(listOf(3,9,8,9,10,9,4,9,99,-1,8))
// computer(listOf(3,9,7,9,10,9,4,9,99,-1,8))

// computer(listOf(3,3,1108,-1,8,3,4,3,99))
// computer(listOf(3,3,1107,-1,8,3,4,3,99))

// computer(listOf(3,12,6,12,15,1,13,14,13,4,13,99,-1,0,1,9))
// computer(listOf(3,3,1105,-1,9,1101,0,0,12,4,12,99,1))

// computer(listOf(3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31,1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104,999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99).toMutableList())
