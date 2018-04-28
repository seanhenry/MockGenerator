package codes.seanhenry.mockgenerator.util

abstract class StringDecorator(private val decorator: StringDecorator?) {

  fun process(string: String): String {
    val decorated = decorate(string)
    return decorator?.process(decorated) ?: decorated
  }

  protected abstract fun decorate(string: String): String
}
