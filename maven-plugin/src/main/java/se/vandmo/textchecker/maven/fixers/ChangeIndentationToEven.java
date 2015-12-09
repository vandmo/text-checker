package se.vandmo.textchecker.maven.fixers;

import static se.vandmo.textchecker.maven.rules.IndentationIsEven.isOk;

import se.vandmo.textchecker.maven.Content;
import se.vandmo.textchecker.maven.ContentType;
import se.vandmo.textchecker.maven.Fixer;


public final class ChangeIndentationToEven implements Fixer {

    @Override
    public void fix(Content content) {
        content.modifyLines((line) -> {
            return possiblyFixLine(content.type(), line);
        });
    }

    private String possiblyFixLine(ContentType contentType, String line) {
        if (isOk(line, contentType)) {
            return line;
        }
        return fixLine(line);
    }

    private String fixLine(String line) {
        return " " + line;
    }

}
