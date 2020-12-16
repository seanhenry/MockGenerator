package codes.seanhenry.mockgenerator.generator.templates

import codes.seanhenry.mockgenerator.generator.MockTransformer
import java.io.File

abstract class MockGeneratorTestTemplate {

  abstract fun build(generator: MockTransformer)

  fun getExpected(type: String): String {
    return File("src/test/resources/" + type + "/" + this::class.simpleName + ".swift").readText(Charsets.UTF_8)
  }
}
