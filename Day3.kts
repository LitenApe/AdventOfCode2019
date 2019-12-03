import java.io.File

operator fun Pair<Int, Int>.times(scalar: Int): Pair<Int, Int>
  = Pair(this.first * scalar, this.second * scalar)

operator fun Pair<Int, Int>.plus(vector: Pair<Int, Int>): Pair<Int, Int>
  = Pair(this.first + vector.first, this.second + vector.second)

fun getDir(dir: Char): Pair<Int, Int>
  = when (dir) {
    'R' -> Pair(1, 0)
    'L' -> Pair(-1, 0)
    'U' -> Pair(0, 1)
    'D' -> Pair(0, -1)
    else -> throw Exception("Shit went ham")
  }

fun trace(route: List<Pair<Char, Int>>): List<Pair<Int, Int>> {
  return route.fold(listOf(Pair(0, 0))) { acc, (dir, dist) ->
    val normalVector = getDir(dir)
    acc.plus((1..dist).map { acc.last() + normalVector * it })
  }.drop(1)
}

fun solveA(routeA: List<Pair<Char, Int>>, routeB: List<Pair<Char, Int>>): Int? {
  val wireA = trace(routeA)
  val wireB = trace(routeB)
  val man = wireA.intersect(wireB).map { Math.abs(it.first) + Math.abs(it.second) }
  return man.min()
}

fun solveB(routeA: List<Pair<Char, Int>>, routeB: List<Pair<Char, Int>>): Int {
  val wireA = trace(routeA)
  val wireB = trace(routeB)
  val intersections = wireA.intersect(wireB)
  val man = wireA.mapIndexed { index, it -> if (intersections.contains(it)) index + wireB.indexOf(it) + 2 else -1 }
  return man.first { it > -1 }
}

fun readFile(filename: String): List<List<Pair<Char, Int>>>
  = File(filename).readLines().subList(0, 2).map { it.split(",").map { Pair(it[0], it.substring(1).toInt()) } } 

val inputs = readFile("./data/day3.txt")

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
