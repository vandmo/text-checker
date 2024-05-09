package se.vandmo.textchecker.maven.rules;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

import java.util.Collection;
import se.vandmo.textchecker.maven.Complaint;
import se.vandmo.textchecker.maven.Content;
import se.vandmo.textchecker.maven.Rule;
import se.vandmo.textchecker.maven.annotations.FixWith;
import se.vandmo.textchecker.maven.fixers.AddEndingLineSeparator;

@FixWith(AddEndingLineSeparator.class)
public final class EndsWithLineSeparator implements Rule {

  @Override
  public Collection<Complaint> check(Content content) {
    String data = content.data();
    if (!isOk(data)) {
      return asList(new Complaint("File does not end with line separator"));
    }
    return emptyList();
  }

  public static boolean isOk(String data) {
    return data.endsWith("\n");
  }
}
