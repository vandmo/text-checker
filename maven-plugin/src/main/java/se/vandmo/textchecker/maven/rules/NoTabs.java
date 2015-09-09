package se.vandmo.textchecker.maven.rules;

import se.vandmo.textchecker.maven.fixers.TabsToSpaces;
import java.util.Collection;
import se.vandmo.textchecker.maven.Complaint;
import se.vandmo.textchecker.maven.Fixer;
import se.vandmo.textchecker.maven.Rule;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.Collections.emptyList;


public final class NoTabs implements Rule {

    @Override
    public Collection<Complaint> check(String content) {
        if (content.contains("\t")) {
            return newArrayList(new Complaint("File contains tab characters"));
        }
        return emptyList();
    }

    @Override
    public Fixer getFixer() {
        return new TabsToSpaces();
    }

}
