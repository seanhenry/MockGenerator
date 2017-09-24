package codes.seanhenry.helpers;

public class KeywordsStore extends Store {

  public boolean isSwiftKeyword(String input) {
    return getProperties().containsKey(input);
  }

  @Override
  protected String getPropertiesFileName() {
    return "keywords.properties";
  }
}
