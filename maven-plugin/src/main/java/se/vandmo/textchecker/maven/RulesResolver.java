package se.vandmo.textchecker.maven;

import static java.util.Arrays.asList;

import java.io.File;
import java.util.Collection;

import se.vandmo.textchecker.maven.rules.NoTabs;


public final class RulesResolver {

  public Collection<Rule> getRulesFor(File file) {
    return asList(new NoTabs() /*, new IndentationIsFour()*/);
  }

}
