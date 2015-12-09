package se.vandmo.textchecker.maven.rules;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.Collections.emptyList;
import static org.apache.commons.lang3.StringUtils.isBlank;

import java.util.Collection;
import java.util.regex.Pattern;

import se.vandmo.textchecker.maven.Complaint;
import se.vandmo.textchecker.maven.Content;
import se.vandmo.textchecker.maven.Rule;
import se.vandmo.textchecker.maven.annotations.FixWith;
import se.vandmo.textchecker.maven.fixers.RemoveTrailingWhitespaceOnNonBlankLines;

@FixWith(RemoveTrailingWhitespaceOnNonBlankLines.class)
public final class NoTrailingWhitespaceOnNonBlankLines implements Rule {

  public static final Pattern ENDS_WITH_WHITESPACE = Pattern.compile("(?<beforetrailing>.*)\\s+$");

  @Override
  public Collection<Complaint> check(Content content) {
    if (!content.checkLines(NoTrailingWhitespaceOnNonBlankLines::isOk)) {
      return newArrayList(new Complaint("File contains non blank line with trailing whitespace"));
    }
    return emptyList();
  }

  public static boolean isOk(String line) {
    return isBlank(line) || !ENDS_WITH_WHITESPACE.matcher(line).matches();
  }

}
