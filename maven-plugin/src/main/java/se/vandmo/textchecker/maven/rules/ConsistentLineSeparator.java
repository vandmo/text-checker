package se.vandmo.textchecker.maven.rules;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.Collections.emptyList;

import java.util.Collection;
import java.util.regex.Pattern;

import se.vandmo.textchecker.maven.Complaint;
import se.vandmo.textchecker.maven.Content;
import se.vandmo.textchecker.maven.Rule;
import se.vandmo.textchecker.maven.fixers.MakeLineSeparatorsConsistent;

@FixWith(MakeLineSeparatorsConsistent.class)
public final class ConsistentLineSeparator implements Rule {

  private static final Pattern N_WITHOUT_PRECEEDING_R = Pattern.compile("[^\r]\n");

  @Override
  public Collection<Complaint> check(Content content) {
    if (!isOk(content.data())) {
      return newArrayList(new Complaint("File has inconsistent line endings"));
    }
    return emptyList();
  }

  public static boolean isOk(String data) {
    if (!data.contains("\r")) {
      return true;
    }
    return !N_WITHOUT_PRECEEDING_R.matcher(data).matches();
  }

}
