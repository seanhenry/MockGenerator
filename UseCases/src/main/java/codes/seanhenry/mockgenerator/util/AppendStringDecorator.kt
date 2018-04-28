package codes.seanhenry.mockgenerator.util

class AppendStringDecorator(decorator: StringDecorator?, private val suffix: String) : StringDecorator(decorator) {

  public override fun decorate(string: String): String {
    if (string.isEmpty()) return ""
    return if (suffix.isEmpty()) string else string + suffix
  }
}
