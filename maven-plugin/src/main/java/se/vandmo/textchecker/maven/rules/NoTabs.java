package se.vandmo.textchecker.maven.rules;

import se.vandmo.textchecker.maven.annotations.FixWith;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.Collections.emptyList;

import java.util.Collection;

import se.vandmo.textchecker.maven.Complaint;
import se.vandmo.textchecker.maven.Content;
import se.vandmo.textchecker.maven.Rule;
import se.vandmo.textchecker.maven.fixers.TabsToSpaces;

@FixWith(TabsToSpaces.class)
public final class NoTabs implements Rule {

  @Override
  public Collection<Complaint> check(Content content) {
    if (content.data().contains("\t")) {
      return newArrayList(new Complaint("File contains tab characters"));
    }
    return emptyList();
  }

}
