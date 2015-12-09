package se.vandmo.textchecker.maven;

import static java.util.Arrays.asList;

import java.io.File;
import java.util.Collection;

import se.vandmo.textchecker.maven.rules.ConsistentLineSeparator;
import se.vandmo.textchecker.maven.rules.IndentationIsEven;
import se.vandmo.textchecker.maven.rules.NoTabs;
import se.vandmo.textchecker.maven.rules.NoTrailingWhitespaceOnNonBlankLines;
import se.vandmo.textchecker.maven.rules.NoWhitespaceOnBlankLines;
import se.vandmo.textchecker.maven.rules.NotExecutable;


public final class RulesResolver {

  public Collection<Rule> getRulesFor(File file) {
    return asList(new NoTabs(),
      new IndentationIsEven(),
      new NoTrailingWhitespaceOnNonBlankLines(),
      new NoWhitespaceOnBlankLines(),
      new NotExecutable(),
      new ConsistentLineSeparator());
  }

}
