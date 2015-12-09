package se.vandmo.textchecker.maven.fixers;

import static se.vandmo.textchecker.maven.rules.NoWhitespaceOnBlankLines.isOk;

import se.vandmo.textchecker.maven.Content;
import se.vandmo.textchecker.maven.Fixer;


public final class RemoveWhitespaceOnBlankLines implements Fixer {

  @Override
  public void fix(Content content) {
    content.modifyLines((line) -> {
      return possiblyFixLine(line);
    });
  }

  private String possiblyFixLine(String line) {
    if (isOk(line)) {
      return line;
    }
    return "";
  }

}
