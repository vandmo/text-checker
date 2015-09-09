package se.vandmo.textchecker.maven;

import java.io.File;
import static java.util.Arrays.asList;
import java.util.Collection;
import se.vandmo.textchecker.maven.rules.EvenIndentation;
import se.vandmo.textchecker.maven.rules.NoTabs;


public final class RulesResolver {

    public Collection<Rule> getRulesFor(File file) {
        return asList(new NoTabs(), new EvenIndentation());
    }
    
}
