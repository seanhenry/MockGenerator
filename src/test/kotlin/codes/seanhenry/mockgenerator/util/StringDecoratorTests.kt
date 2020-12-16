package codes.seanhenry.mockgenerator.util

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class StringDecoratorTests  {

  internal inner class EmptyStringDecorator(decorator: StringDecorator?) : StringDecorator(decorator) {

    override fun decorate(string: String): String {
      return string
    }
  }

  @Test
  fun testShouldProcessStringInDecorator() {
    val decorator = PrependStringDecorator(null, "prefix")
    val stringDecorator = EmptyStringDecorator(decorator)
    assertEquals("prefixName", stringDecorator.process("name"))
  }

  @Test
  fun testShouldNotGiveStringToNullDecorator() {
    val stringDecorator = EmptyStringDecorator(null)
    assertEquals("name", stringDecorator.process("name"))
  }

  @Test
  fun testShouldProcessStringInMultipleDecorators() {
    val appendDecorator = AppendStringDecorator(null, "Suffix")
    val prependDecorator = PrependStringDecorator(appendDecorator, "prefix")
    val stringDecorator = EmptyStringDecorator(prependDecorator)
    assertEquals("prefixNameSuffix", stringDecorator.process("name"))
  }
}
