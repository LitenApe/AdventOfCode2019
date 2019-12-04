fun ascending(value: String): Boolean
  = value.foldIndexed(true) { index, acc, elem -> if (acc == false || index == 5) acc else elem <= value.get(index + 1) }

val min = 153517
val max = 630395

// permitations with groups and ascending: 1 729
val adjacent = Regex("(\\d)\\1")
val solveA = (min until max).toList().map { it.toString() }.filter { ascending(it) && adjacent.containsMatchIn(it) }

// permutations with group of two: 1172
val re = Regex("(\\d)\\1+")
val solveB = solveA.filter { re.findAll(it).toList().map { it.value.length == 2 }.contains(true) }

println(solveA.size)
println(solveB.size)
