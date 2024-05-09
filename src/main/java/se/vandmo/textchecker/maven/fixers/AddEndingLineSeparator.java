package se.vandmo.textchecker.maven.fixers;

import static java.lang.System.lineSeparator;
import static se.vandmo.textchecker.maven.rules.EndsWithLineSeparator.isOk;

import se.vandmo.textchecker.maven.Content;
import se.vandmo.textchecker.maven.Fixer;

public final class AddEndingLineSeparator implements Fixer {

  @Override
  public void fix(Content content) {
    if (!isOk(content.data())) {
      content.data(content.data() + lineSeparator());
    }
  }
}
