package se.vandmo.textchecker.maven.rules;

import static com.google.common.collect.Lists.newArrayList;
import java.util.Collection;
import static java.util.Collections.emptyList;
import static org.apache.commons.lang3.StringUtils.indexOfAnyBut;
import static org.apache.commons.lang3.StringUtils.isBlank;
import se.vandmo.textchecker.maven.Complaint;
import se.vandmo.textchecker.maven.Fixer;
import se.vandmo.textchecker.maven.Rule;
import se.vandmo.textchecker.maven.fixers.ChangeIndentationToFour;
import static se.vandmo.textchecker.maven.utils.Utils.allLinesAreOk;


public final class IndentationIsFour implements Rule {

    @Override
    public Collection<Complaint> check(String content) {
        if (!allLinesAreOk(content, this::isLineOk)) {
            return newArrayList(new Complaint("Indentation needs to be a multiple of four"));
        }
        return emptyList();
    }

    private boolean isLineOk(String line) {
        if (isBlank(line)) {
            // ignore blank lines when checking, could be interpreted as trailing whitespace
            return true;
        }
        int index = indexOfAnyBut(line, ' ');
        if (index < 0) {
            // no whitespace in line
            return true;
        }
        return index % 4 == 0;
    }

    @Override
    public Fixer getFixer() {
        return new ChangeIndentationToFour();
    }

}
