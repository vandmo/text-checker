package se.vandmo.textchecker.maven.rules;

import se.vandmo.textchecker.maven.fixers.MakeIndentationEven;
import static com.google.common.collect.Lists.newArrayList;
import java.util.Collection;
import se.vandmo.textchecker.maven.Complaint;
import se.vandmo.textchecker.maven.Fixer;
import se.vandmo.textchecker.maven.Rule;
import static java.util.Collections.emptyList;
import static org.apache.commons.lang3.StringUtils.indexOfAnyBut;


public final class EvenIndentation implements Rule {

    @Override
    public Collection<Complaint> check(String content) {
        int index = indexOfAnyBut(content, ' ');
        if ((index & 1) == 1) {
            return newArrayList(new Complaint("Indentation needs to be an even number of spaces"));
        }
        return emptyList();
    }

    @Override
    public Fixer getFixer() {
        return new MakeIndentationEven();
    }

}
