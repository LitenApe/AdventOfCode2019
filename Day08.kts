import java.io.File

fun calculateChecksum(layer: Map<Char, List<Pair<Char, Int>>>): Pair<Int, Int>
  = Pair(layer.get('0')!![0].second, layer.get('1')!![0].second * layer.get('2')!![0].second)

fun getChecksum(layers: List<String>): Int
  = layers.map { it.toCharArray().toList() }
      .map { it.groupBy { it } }
      .map { it.map { it.key to it.value.size } }
      .map { it.groupBy { it.first } }
      .map { calculateChecksum(it) }
      .sortedWith( compareBy( { it.first } ) )[0].second

fun decodeImage(layers: List<String>): List<String> {
  var image = layers[0].toCharArray().toList()
  for ( layer in layers ) {
    image = layer.mapIndexed { index, value -> if (image.get(index) == '2') value else image.get(index) }
  }
  return image.joinToString("").replace('0', ' ').chunked(25)
}

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
