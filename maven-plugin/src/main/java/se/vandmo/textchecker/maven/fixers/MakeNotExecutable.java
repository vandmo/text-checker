package se.vandmo.textchecker.maven.fixers;

import se.vandmo.textchecker.maven.Content;
import se.vandmo.textchecker.maven.Fixer;


public final class MakeNotExecutable implements Fixer {

  @Override
  public void fix(Content content) {
    content.executable(false);
  }

}
