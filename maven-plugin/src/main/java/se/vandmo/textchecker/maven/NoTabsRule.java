package se.vandmo.textchecker.maven;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.Collections.emptyList;


public final class NoTabsRule implements Rule {

    @Override
    public Collection<Complaint> check(File file) throws Exception {
        try (InputStream stream = new FileInputStream(file)) {
            return check(stream);
        }
    }

    private Collection<Complaint> check(InputStream stream) throws IOException {
        do {
            int c = stream.read();
            if (c < 0) {
                break;
            }
            if (c == '\t') {
                return newArrayList(new Complaint("File contains tab characters"));
            }
        } while (true);
        return emptyList();
    }

    @Override
    public Fixer getFixer() {
        return null;
    }

}
