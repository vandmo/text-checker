package se.vandmo.textchecker.maven.fixers;

import se.vandmo.textchecker.maven.Fixer;
import static org.apache.commons.lang3.StringUtils.indexOfAnyBut;
import static org.apache.commons.lang3.StringUtils.repeat;


public final class MakeIndentationEven implements Fixer {

    @Override
    public String fix(String content) {
        int index = indexOfAnyBut(content, ' ');
        if ((index & 1) == 1) {
            String afterIndentation = content.substring(index);
            return repeat(' ', index+1) + afterIndentation;
        }
        return content;
    }

}
