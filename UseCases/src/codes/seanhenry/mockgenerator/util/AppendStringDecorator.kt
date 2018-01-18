package codes.seanhenry.mockgenerator.util

class AppendStringDecorator(decorator: StringDecorator?, private val suffix: String) : StringDecorator(decorator) {

  public override fun decorate(string: String): String {
    if (string == null || string.isEmpty()) return ""
    return if (suffix == null || suffix.isEmpty()) string else string + suffix
  }
}
