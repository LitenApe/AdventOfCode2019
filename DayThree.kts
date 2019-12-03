import java.io.File

fun trace(route: List<Pair<Char, Int>>, log: List<Pair<Int, Int>> = listOf(Pair(0, 0))): List<Pair<Int, Int>> {
  if (route.size == 0) return log.subList(1, log.size) // Omits the start coord which is always (0, 0)

  val cur = log.last()
  val (dir, dist) = route.first()

  val newLog = when (dir) {
    'R' -> (1..dist).map { Pair(cur.first + it, cur.second) }
    'L' -> (1..dist).map { Pair(cur.first - it, cur.second) }
    'U' -> (1..dist).map { Pair(cur.first, cur.second + it) }
    'D' -> (1..dist).map { Pair(cur.first, cur.second - it) }
    else -> throw Exception("Shit went ham")
  }

  val remaining = route.subList(1, route.size)
  return trace(remaining, log + newLog)
}

fun solveA(routeA: List<Pair<Char, Int>>, routeB: List<Pair<Char, Int>>): Int? {
  val wireA = trace(routeA)
  val wireB = trace(routeB)
  val man = wireA.intersect(wireB).map { Math.abs(it.first) + Math.abs(it.second) }
  return man.min()
}

fun solveB(routeA: List<Pair<Char, Int>>, routeB: List<Pair<Char, Int>>): Int {
  val wireA = trace(routeA)
  val wireB = trace(routeB).toSet()
  val man = wireA.mapIndexed { index, it -> if (wireB.indexOf(it) > -1 ) index + wireB.indexOf(it) + 2 else -1 }
  return man.first { it > -1 }
}

fun readFile(filename: String): List<List<Pair<Char, Int>>>
  = File(filename).readLines().subList(0, 2).map { it.split(",").map { Pair(it[0], it.substring(1).toInt()) } } 

val inputs = readFile("./data/dayThree.txt")

val testA = listOf("R8,U5,L5,D3", "U7,R6,D4,L4").map { it.split(",").map { Pair(it[0], it.substring(1).toInt()) } }
val testB = listOf("R75,D30,R83,U83,L12,D49,R71,U7,L72", "U62,R66,U55,R34,D71,R55,D58,R83").map { it.split(",").map { Pair(it[0], it.substring(1).toInt()) } }
val testC = listOf("R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51", "U98,R91,D20,R16,D67,R40,U7,R15,U6,R7").map { it.split(",").map { Pair(it[0], it.substring(1).toInt()) } }
val cases = listOf(testA, testB, testC, inputs)

for ( case in cases) { // A: 6, B: 159, C: 135, res: 1626
  println(solveA(case[0], case[1]))
}

for ( case in cases) { // A: 30, B: 610, C: 410, res: 27330
  println(solveB(case[0], case[1]))
}
