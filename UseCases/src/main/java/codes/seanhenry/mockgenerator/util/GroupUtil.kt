package codes.seanhenry.mockgenerator.util

class GroupUtil(private val open: Char, private val closed: Char) {

  private lateinit var openMap: MutableMap<Int, Int>
  private lateinit var rangeMap: MutableMap<Int, IntRange>
  private var nextIndex = 0

  fun findGroups(string: String): Map<Int, IntRange> {
    openMap = HashMap()
    rangeMap = HashMap()
    nextIndex = 0
    string.forEachIndexed { i, char ->
      if (char == open) {
        openMap[nextIndex] = i
        nextIndex++
      } else if (char == closed) {
        completeRangeAt(i, findLastOpenIndex())
      }
    }
    return rangeMap
  }

  private fun completeRangeAt(end: Int, index: Int) {
    val start = openMap[index]
    if (start != null) {
      rangeMap[index] = IntRange(start, end)
    }
    openMap.remove(index)
  }

  private fun findLastOpenIndex(): Int {
    var i = nextIndex - 1
    while (openMap[i] == null && i >= 0) {
      i--
    }
    return i
  }
}
