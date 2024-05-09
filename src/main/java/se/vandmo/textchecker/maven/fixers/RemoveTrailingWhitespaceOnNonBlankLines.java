package se.vandmo.textchecker.maven.fixers;

import static se.vandmo.textchecker.maven.rules.NoTrailingWhitespaceOnNonBlankLines.ENDS_WITH_WHITESPACE;
import static se.vandmo.textchecker.maven.rules.NoTrailingWhitespaceOnNonBlankLines.isOk;

import java.util.regex.Matcher;
import se.vandmo.textchecker.maven.Content;
import se.vandmo.textchecker.maven.ContentType;
import se.vandmo.textchecker.maven.Fixer;

public final class RemoveTrailingWhitespaceOnNonBlankLines implements Fixer {

  @Override
  public void fix(Content content) {
    content.modifyLines(
        (line) -> {
          return possiblyFixLine(line, content.type());
        });
  }

  private String possiblyFixLine(String line, ContentType contentType) {
    if (isOk(line, contentType)) {
      return line;
    }
    Matcher matcher = ENDS_WITH_WHITESPACE.matcher(line);
    if (!matcher.matches()) {
      return line;
    }
    return matcher.group("beforetrailing");
  }
}
