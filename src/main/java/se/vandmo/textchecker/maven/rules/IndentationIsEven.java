package se.vandmo.textchecker.maven.rules;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.apache.commons.lang3.StringUtils.indexOfAnyBut;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static se.vandmo.textchecker.maven.ContentType.JAVA;

import java.util.Collection;
import se.vandmo.textchecker.maven.Complaint;
import se.vandmo.textchecker.maven.Content;
import se.vandmo.textchecker.maven.ContentType;
import se.vandmo.textchecker.maven.Rule;
import se.vandmo.textchecker.maven.annotations.FixWith;
import se.vandmo.textchecker.maven.fixers.ChangeIndentationToEven;

@FixWith(ChangeIndentationToEven.class)
public final class IndentationIsEven implements Rule {

  @Override
  public Collection<Complaint> check(Content content) {
    if (hasInvalidIndentation(content)) {
      return asList(new Complaint("Indentation needs to be an even number"));
    }
    return emptyList();
  }

  private boolean hasInvalidIndentation(Content content) {
    return !content.checkLines(
        (line) -> {
          return isLineOk(line, content.type());
        });
  }

  private boolean isLineOk(String line, ContentType contentType) {
    if (isBlank(line)) {
      // ignore blank lines when checking, could be interpreted as trailing whitespace on empty line
      return true;
    }
    return isOk(line, contentType);
  }

  public static boolean isOk(String line, ContentType contentType) {
    int index = indexOfAnyBut(line, ' ');
    if (index < 0) {
      // no whitespace in line
      return true;
    }
    if (index % 2 == 0) {
      return true;
    }
    if (contentType.equals(JAVA)) {
      String afterIndentation = line.substring(index);
      if (afterIndentation.startsWith("*")) {
        return true;
      }
    }
    return false;
  }
}
