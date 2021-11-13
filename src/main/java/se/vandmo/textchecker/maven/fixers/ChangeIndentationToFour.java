package se.vandmo.textchecker.maven.fixers;

import static org.apache.commons.lang3.StringUtils.indexOfAnyBut;
import static org.apache.commons.lang3.StringUtils.repeat;
import static se.vandmo.textchecker.maven.rules.IndentationIsFour.isOk;

import se.vandmo.textchecker.maven.Content;
import se.vandmo.textchecker.maven.ContentType;
import se.vandmo.textchecker.maven.Fixer;


public final class ChangeIndentationToFour implements Fixer {

  @Override
  public void fix(Content content) {
    content.modifyLines((line) -> {
      return possiblyFixLine(content.type(), line);
    });
  }

  private String possiblyFixLine(ContentType contentType, String line) {
    if (isOk(line, contentType)) {
      return line;
    }
    return fixLine(line);
  }

  private String fixLine(String line) {
    int index = indexOfAnyBut(line, ' ');
    String afterIndentation = line.substring(index);
    return repeat("    ", (index / 4) + 1) + afterIndentation;
  }

}
