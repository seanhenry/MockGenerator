package codes.seanhenry.mockgenerator.entities

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class DictionaryTypeBuilderTest {

  @Test
  fun testShouldBuildEmptyDictionary() {
    val dictionary = DictionaryType.Builder().build()
    assertEquals("[: ]", dictionary.text)
  }

  @Test
  fun testShouldBuildKeyTypeDictionary() {
    val dictionary = DictionaryType.Builder().keyType("Key").build()
    assertEquals("Key", dictionary.keyType.text)
    assertEquals("[Key: ]", dictionary.text)
  }

  @Test
  fun testShouldBuildAnyKeyTypeDictionary() {
    val dictionary = DictionaryType.Builder().keyType().optional { it.type("Type") }.build()
    assertEquals("Type?", dictionary.keyType.text)
    assertEquals("[Type?: ]", dictionary.text)
  }

  @Test
  fun testShouldBuildValueTypeDictionary() {
    val dictionary = DictionaryType.Builder().valueType("Type").build()
    assertEquals("Type", dictionary.valueType.text)
    assertEquals("[: Type]", dictionary.text)
  }

  @Test
  fun testShouldBuildAnyValueTypeDictionary() {
    val dictionary = DictionaryType.Builder().valueType().optional { it.type("Type") }.build()
    assertEquals("Type?", dictionary.valueType.text)
    assertEquals("[: Type?]", dictionary.text)
  }

  @Test
  fun testShouldBuildDictionary() {
    val dictionary = DictionaryType.Builder()
        .keyType("Int")
        .valueType().optional { it.type("Type") }
        .build()
    assertEquals("Int", dictionary.keyType.text)
    assertEquals("Type?", dictionary.valueType.text)
    assertEquals("[Int: Type?]", dictionary.text)
  }

  @Test
  fun testShouldBuildVerboseDictionary() {
    val dictionary = DictionaryType.Builder()
        .keyType("Int")
        .valueType().optional { it.type("Type") }
        .verbose()
        .build()
    assertEquals("Int", dictionary.keyType.text)
    assertEquals("Type?", dictionary.valueType.text)
    assertEquals("Dictionary<Int, Type?>", dictionary.text)
  }
}
