package se.vandmo.textchecker.maven.fixers;

import se.vandmo.textchecker.maven.Fixer;


public final class TabsToSpaces implements Fixer {

    @Override
    public String fix(String content) {
        return content.replace("\t", "    ");
    }

}
