package se.vandmo.textchecker.maven.rules;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.util.Collection;
import se.vandmo.textchecker.maven.Complaint;
import se.vandmo.textchecker.maven.Content;
import se.vandmo.textchecker.maven.Rule;
import se.vandmo.textchecker.maven.annotations.FixWith;
import se.vandmo.textchecker.maven.fixers.RemoveWhitespaceOnBlankLines;

@FixWith(RemoveWhitespaceOnBlankLines.class)
public final class NoWhitespaceOnBlankLines implements Rule {

  @Override
  public Collection<Complaint> check(Content content) {
    if (!content.checkLines(NoWhitespaceOnBlankLines::isOk)) {
      return asList(new Complaint("File contains whitespace on blank line"));
    }
    return emptyList();
  }

  public static boolean isOk(String line) {
    return isNotBlank(line) || line.length() == 0;
  }
}
