package codes.seanhenry.template;

import codes.seanhenry.mockgenerator.generator.MockView;
import codes.seanhenry.mockgenerator.generator.MockViewModel;
import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.stream.Collectors;

public class MustacheView implements MockView {

  private String result = "";
  private final String fileName;

  public MustacheView(String fileName) {
    this.fileName = fileName;
  }

  @Override
  public void render(@NotNull MockViewModel model) {
    StringWriter writer = new StringWriter();
    DefaultMustacheFactory mf = new DefaultMustacheFactory();
    Mustache mustache = mf.compile(fileName + ".mustache");
    try {
      mustache.execute(writer, model).flush();
    } catch (IOException ignored) {}
    String[] lines = new String(writer.getBuffer()).split("\\n");
    result = Arrays.stream(lines)
        .map(String::trim)
        .filter(l -> !l.isEmpty())
        .collect(Collectors.joining("\n"));
  }

  public String getResult() {
    return result;
  }
}
