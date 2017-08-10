package codes.seanhenry.helpers;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public abstract class Store {

  private Properties defaultValues;

  protected abstract String getPropertiesFileName();

  protected Properties getProperties() {
    if (defaultValues != null) {
      return defaultValues;
    }
    try {
      defaultValues = createProperties();
    } catch (IOException ignored) {}
    return defaultValues;
  }

  private Properties createProperties() throws IOException {
    Properties defaultValues = new Properties();
    InputStream in = getClass().getResourceAsStream(getPropertiesFileName());
    try {
      defaultValues.load(in);
    } catch (Throwable ignored) { }
    finally {
      in.close();
    }
    return defaultValues;
  }
}
