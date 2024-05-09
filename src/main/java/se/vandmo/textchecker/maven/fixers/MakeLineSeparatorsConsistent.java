package se.vandmo.textchecker.maven.fixers;

import static java.lang.System.lineSeparator;
import static org.apache.commons.lang3.StringUtils.remove;
import static org.apache.commons.lang3.StringUtils.replace;

import se.vandmo.textchecker.maven.Content;
import se.vandmo.textchecker.maven.Fixer;

public final class MakeLineSeparatorsConsistent implements Fixer {

  @Override
  public void fix(Content content) {
    content.data(fixLineSeparators(content.data()));
  }

  private String fixLineSeparators(String data) {
    data = remove(data, "\r");
    data = replace(data, "\n", lineSeparator());
    return data;
  }
}
