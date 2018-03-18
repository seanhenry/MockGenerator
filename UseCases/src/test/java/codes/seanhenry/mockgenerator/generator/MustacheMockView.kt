package codes.seanhenry.mockgenerator.generator

import com.github.mustachejava.DefaultMustacheFactory
import java.io.StringWriter

class MustacheMockView(var rendered: String = ""): MockView {

  override fun render(model: MockViewModel) {
    val writer = StringWriter()
    val mf = DefaultMustacheFactory()
    val mustache = mf.compile("mock.mustache")
    mustache.execute(writer, model).flush()
    val lines = String(writer.buffer).split("\n")
    rendered = lines.map { it.trim() }.filter { it.isNotBlank() }.joinToString("\n")
  }
}
