package se.vandmo.textchecker.maven;

import java.util.Collection;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.Collections.emptyList;


public final class NoTabsRule implements Rule {

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
