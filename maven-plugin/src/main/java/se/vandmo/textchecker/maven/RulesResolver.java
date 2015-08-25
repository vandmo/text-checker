package se.vandmo.textchecker.maven;

import java.io.File;
import java.util.Collection;

import static java.util.Arrays.asList;


public final class RulesResolver {

    public Collection<Rule> getRulesFor(File file) {
        return asList(new NoTabsRule());
    }
    
}
