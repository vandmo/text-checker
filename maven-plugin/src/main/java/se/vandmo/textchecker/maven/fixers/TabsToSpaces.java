package se.vandmo.textchecker.maven.fixers;

import se.vandmo.textchecker.maven.Content;
import se.vandmo.textchecker.maven.Fixer;


public final class TabsToSpaces implements Fixer {

    @Override
    public void fix(Content content) {
        content.data(content.data().replace("\t", "  "));
    }

}
