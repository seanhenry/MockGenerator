package codes.seanhenry.mockgenerator.algorithms

import codes.seanhenry.mockgenerator.entities.*
import org.junit.jupiter.api.Test

class CopyVisitorTest {

  @Test
  fun testShouldCopy() {
    assertCopied(TypeIdentifier("Type"))
    assertCopied(FunctionType.Builder().build())
    assertCopied(OptionalType.Builder().build())
    assertCopied(TupleType.Builder().element("Type").build())
    assertCopied(ArrayType.Builder().build())
    assertCopied(DictionaryType.Builder().build())
    assertCopied(GenericType.Builder("Type").build())
  }

  @Test
  private fun assertCopied(original: Type) {
    val copied = CopyVisitor.copy(original)
    assert(original !== copied)
    assert(original == copied)
  }
}
