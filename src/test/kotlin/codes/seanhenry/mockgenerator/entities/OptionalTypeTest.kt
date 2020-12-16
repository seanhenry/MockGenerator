package codes.seanhenry.mockgenerator.entities

import org.junit.jupiter.api.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class OptionalTypeTest {

  @Test
  fun testShouldDeepCopy() {
    val original = OptionalType.Builder().type("Type").build()
    val copied = original.deepCopy()
    assertTrue(original == copied)
    assertFalse(original === copied)
    assertTrue(original.type == copied.type)
    assertFalse(original.type === copied.type)
  }
}
