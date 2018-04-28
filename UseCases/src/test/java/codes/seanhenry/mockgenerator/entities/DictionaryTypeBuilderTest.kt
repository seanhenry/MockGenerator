package codes.seanhenry.mockgenerator.entities

import junit.framework.TestCase

class DictionaryTypeBuilderTest: TestCase() {

  fun testShouldBuildEmptyDictionary() {
    val dictionary = DictionaryType.Builder().build()
    assertEquals("[: ]", dictionary.text)
  }

  fun testShouldBuildKeyTypeDictionary() {
    val dictionary = DictionaryType.Builder().keyType("Key").build()
    assertEquals("Key", dictionary.keyType.text)
    assertEquals("[Key: ]", dictionary.text)
  }

  fun testShouldBuildAnyKeyTypeDictionary() {
    val dictionary = DictionaryType.Builder().keyType().optional { it.type("Type") }.build()
    assertEquals("Type?", dictionary.keyType.text)
    assertEquals("[Type?: ]", dictionary.text)
  }

  fun testShouldBuildValueTypeDictionary() {
    val dictionary = DictionaryType.Builder().valueType("Type").build()
    assertEquals("Type", dictionary.valueType.text)
    assertEquals("[: Type]", dictionary.text)
  }

  fun testShouldBuildAnyValueTypeDictionary() {
    val dictionary = DictionaryType.Builder().valueType().optional { it.type("Type") }.build()
    assertEquals("Type?", dictionary.valueType.text)
    assertEquals("[: Type?]", dictionary.text)
  }

  fun testShouldBuildDictionary() {
    val dictionary = DictionaryType.Builder()
        .keyType("Int")
        .valueType().optional { it.type("Type") }
        .build()
    assertEquals("Int", dictionary.keyType.text)
    assertEquals("Type?", dictionary.valueType.text)
    assertEquals("[Int: Type?]", dictionary.text)
  }

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
