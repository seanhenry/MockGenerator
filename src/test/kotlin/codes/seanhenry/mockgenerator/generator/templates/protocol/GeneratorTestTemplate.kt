package codes.seanhenry.mockgenerator.generator.templates.protocol

import codes.seanhenry.mockgenerator.generator.Generator
import java.io.File

abstract class GeneratorTestTemplate {

  abstract fun build(generator: Generator)

  fun getExpected(type: String): String {
    return File("src/test/resources/" + type + "/protocol/" + this::class.simpleName + ".swift").readText(Charsets.UTF_8)
  }
}
