package codes.seanhenry.mockgenerator.entities

import org.junit.jupiter.api.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class DictionaryTypeTest {

  @Test
  fun testShouldDeepCopy() {
    val original = DictionaryType.Builder()
        .keyType("Type")
        .valueType("Type")
        .build()
    val copied = original.deepCopy()
    assertTrue(original == copied)
    assertFalse(original === copied)
    assertFalse(original.keyType === copied.keyType)
    assertFalse(original.valueType === copied.valueType)
  }
}
