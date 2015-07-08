package se.vandmo.textchecker.maven;

import java.io.File;
import java.util.Collection;


public final class NoTabsRule implements Rule {

    public Collection<Complaint> check(File file) {
        return null;
    }

    public Fixer getFixer() {
        return null;
    }

}
