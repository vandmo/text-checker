package se.vandmo.textchecker.maven.fixers;

import static org.codehaus.plexus.util.StringUtils.isNotBlank;
import static se.vandmo.textchecker.maven.rules.NoTrailingWhitespaceOnNonBlankLines.ENDS_WITH_WHITESPACE;

import java.util.regex.Matcher;

import se.vandmo.textchecker.maven.Content;
import se.vandmo.textchecker.maven.Fixer;


public final class RemoveTrailingWhitespaceOnNonBlankLines implements Fixer {

  @Override
  public void fix(Content content) {
    content.modifyLines((line) -> {
      return possiblyFixLine(line);
    });
  }

  private String possiblyFixLine(String line) {
    if (isNotBlank(line)) {
      return line;
    }
    Matcher matcher = ENDS_WITH_WHITESPACE.matcher(line);
    if (!matcher.matches()) {
      return line;
    }
    return matcher.group("beforetrailing");
  }

}
