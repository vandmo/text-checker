package se.vandmo.textchecker.maven.rules;

import static com.google.common.collect.Lists.newArrayList;
import java.util.Collection;
import static java.util.Collections.emptyList;
import se.vandmo.textchecker.maven.Complaint;
import se.vandmo.textchecker.maven.Content;
import se.vandmo.textchecker.maven.Fixer;
import se.vandmo.textchecker.maven.Rule;
import se.vandmo.textchecker.maven.fixers.TabsToSpaces;


public final class NoTabs implements Rule {

    @Override
    public Collection<Complaint> check(Content content) {
        if (content.data().contains("\t")) {
            return newArrayList(new Complaint("File contains tab characters"));
        }
        return emptyList();
    }

    @Override
    public Fixer getFixer() {
        return new TabsToSpaces();
    }

}
