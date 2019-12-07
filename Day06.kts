import java.io.File

fun buildPath(current: String, orbitMap: Map<String, String>): List<String>
  = if (current == "COM") listOf("COM") else buildPath(orbitMap.getValue(current), orbitMap) + current

fun jumpDistance(posOne: String, posTwo: String, orbitMap: Map<String, String>): Int {
  val pathOne = buildPath(posOne, orbitMap)
  val pathTwo = buildPath(posTwo, orbitMap)
  val common = pathOne.intersect(pathTwo)
  return (pathOne.size + pathTwo.size - 2) - (common.size * 2)
}

fun readFile(filename: String): Map<String, String>
  = File(filename)
    .readLines()
    .map { it.split(")").let { (x, y) -> y to x } }
    .toMap()

val inputs = readFile("./data/day06.txt")
val a = inputs.keys.map { buildPath(it, inputs).size - 1 }.sum()
val b = jumpDistance("YOU", "SAN", inputs)
println("A: $a | B: $b") // 271151 | 388
