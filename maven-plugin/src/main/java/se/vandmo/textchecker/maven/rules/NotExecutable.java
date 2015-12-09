package se.vandmo.textchecker.maven.rules;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.Collections.emptyList;

import java.util.Collection;

import se.vandmo.textchecker.maven.Complaint;
import se.vandmo.textchecker.maven.Content;
import se.vandmo.textchecker.maven.Fixer;
import se.vandmo.textchecker.maven.Rule;
import se.vandmo.textchecker.maven.fixers.MakeNotExecutable;


public final class NotExecutable implements Rule {

  @Override
  public Collection<Complaint> check(Content content) {
    if (content.executable()) {
      return newArrayList(new Complaint("File is executable"));
    }
    return emptyList();
  }

  @Override
  public Fixer getFixer() {
    return new MakeNotExecutable();
  }

}
