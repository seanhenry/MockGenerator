package codes.seanhenry.mockgenerator.util

import junit.framework.TestCase
import kotlin.test.assertEquals

class StringDecoratorTests : TestCase() {

  internal inner class EmptyStringDecorator(decorator: StringDecorator?) : StringDecorator(decorator) {

    override fun decorate(string: String): String {
      return string
    }
  }

  fun testShouldProcessStringInDecorator() {
    val decorator = PrependStringDecorator(null, "prefix")
    val stringDecorator = EmptyStringDecorator(decorator)
    assertEquals("prefixName", stringDecorator.process("name"))
  }

  fun testShouldNotGiveStringToNullDecorator() {
    val stringDecorator = EmptyStringDecorator(null)
    assertEquals("name", stringDecorator.process("name"))
  }

  fun testShouldProcessStringInMultipleDecorators() {
    val appendDecorator = AppendStringDecorator(null, "Suffix")
    val prependDecorator = PrependStringDecorator(appendDecorator, "prefix")
    val stringDecorator = EmptyStringDecorator(prependDecorator)
    assertEquals("prefixNameSuffix", stringDecorator.process("name"))
  }
}
