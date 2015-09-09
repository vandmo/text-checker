package se.vandmo.textchecker.maven.fixers;

import static org.apache.commons.lang3.StringUtils.indexOfAnyBut;
import static org.apache.commons.lang3.StringUtils.repeat;
import se.vandmo.textchecker.maven.Content;
import se.vandmo.textchecker.maven.Fixer;


public final class ChangeIndentationToFour implements Fixer {

    @Override
    public void fix(Content content) {
        content.modifyLines(this::modifyLine);
    }

    private String modifyLine(String line) {
        int index = indexOfAnyBut(line, ' ');
        if (index < 0) {
            // no whitespace in line
            return line;
        }
        if (index % 4 != 0) {
            String afterIndentation = line.substring(index);
            return repeat("    ", (index / 4) + 1) + afterIndentation;
        }
        return line;
    }

}
