package codes.seanhenry.mockgenerator.util

class PrependStringDecorator(decorator: StringDecorator?, private val prefix: String) : StringDecorator(decorator) {

  override fun decorate(string: String): String {
    if (string == null || string.isEmpty()) return ""
    if (prefix == null || prefix.isEmpty()) return string
    var capitalised = string.substring(0, 1).toUpperCase()
    if (1 < string.length)
      capitalised += string.substring(1)
    return prefix + capitalised
  }
}
