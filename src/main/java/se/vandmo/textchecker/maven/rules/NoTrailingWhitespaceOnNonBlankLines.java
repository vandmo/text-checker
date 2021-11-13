package se.vandmo.textchecker.maven.rules;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.Collections.emptyList;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static se.vandmo.textchecker.maven.ContentType.JAVA;

import java.util.Collection;
import java.util.regex.Pattern;

import se.vandmo.textchecker.maven.Complaint;
import se.vandmo.textchecker.maven.Content;
import se.vandmo.textchecker.maven.ContentType;
import se.vandmo.textchecker.maven.Rule;
import se.vandmo.textchecker.maven.annotations.FixWith;
import se.vandmo.textchecker.maven.fixers.RemoveTrailingWhitespaceOnNonBlankLines;

@FixWith(RemoveTrailingWhitespaceOnNonBlankLines.class)
public final class NoTrailingWhitespaceOnNonBlankLines implements Rule {

  public static final Pattern ENDS_WITH_WHITESPACE = Pattern.compile("(?<beforetrailing>.*)\\s+$");

  @Override
  public Collection<Complaint> check(Content content) {
    if (!content.checkLines(line -> isOk(line, content.type()))) {
      return newArrayList(new Complaint("File contains non blank line with trailing whitespace"));
    }
    return emptyList();
  }

  public static boolean isOk(String line, ContentType contentType) {
    if (isBlank(line)) {
      return true;
    }


    if (contentType.equals(JAVA) && line.trim().equals("*")) {
      return true;
    }

    return !ENDS_WITH_WHITESPACE.matcher(line).matches();
  }

}
