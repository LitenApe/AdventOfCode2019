import java.io.File

fun getChecksum(layers: List<String>): Int
  = layers
    .map { Pair(it.count { it == '0' }, it.count { it == '1' } * it.count { it == '2' }) }
    .minBy { it.first }!!.second

fun decodeImage(layers: List<String>): List<String>
  = layers
    .reduce { acc, layer -> (acc zip layer)
      .map { (x, y) -> if (x == '2') y else x }
      .joinToString("")
    }
    .replace('0', ' ')
    .chunked(25)

fun readFile(filename: String)
  = File(filename)
    .useLines() { it.first() }
    .chunked(25 * 6)

val input = readFile("./data/day08.txt")
val checksum = getChecksum(input)
val image = decodeImage(input)

println(checksum)
for ( layer in image ) println(layer)
/* 2210
 11   11  1111  11  1111
1  1 1  1 1    1  1 1
1    1    111  1    111
1    1 11 1    1 11 1
1  1 1  1 1    1  1 1
 11   111 1111  111 1111
 */
