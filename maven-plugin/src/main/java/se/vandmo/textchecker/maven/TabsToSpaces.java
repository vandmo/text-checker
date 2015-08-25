package se.vandmo.textchecker.maven;


public final class TabsToSpaces implements Fixer {

    @Override
    public String fix(String content) {
        return content.replace("\t", "    ");
    }

}
