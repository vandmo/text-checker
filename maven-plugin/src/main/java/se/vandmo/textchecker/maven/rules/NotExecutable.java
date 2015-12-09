package se.vandmo.textchecker.maven.rules;

import se.vandmo.textchecker.maven.annotations.FixWith;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.Collections.emptyList;

import java.util.Collection;

import se.vandmo.textchecker.maven.Complaint;
import se.vandmo.textchecker.maven.Content;
import se.vandmo.textchecker.maven.Rule;
import se.vandmo.textchecker.maven.fixers.MakeNotExecutable;

@FixWith(MakeNotExecutable.class)
public final class NotExecutable implements Rule {

  @Override
  public Collection<Complaint> check(Content content) {
    if (content.executable()) {
      return newArrayList(new Complaint("File is executable"));
    }
    return emptyList();
  }

}
