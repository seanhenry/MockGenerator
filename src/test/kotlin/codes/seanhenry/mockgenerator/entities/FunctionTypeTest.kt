package codes.seanhenry.mockgenerator.entities

import org.junit.jupiter.api.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class FunctionTypeTest {

  @Test
  fun testShouldDeepCopy() {
    val original = FunctionType.Builder()
        .argument("Type")
        .argument("Type")
        .returnType("Type")
        .build()
    val copied = original.deepCopy()
    assertTrue(original == copied)
    assertFalse(original === copied)
    assertTrue(original.arguments[0] == copied.arguments[0])
    assertTrue(original.arguments[1] == copied.arguments[1])
    assertFalse(original.returnType === copied.returnType)
  }
}
