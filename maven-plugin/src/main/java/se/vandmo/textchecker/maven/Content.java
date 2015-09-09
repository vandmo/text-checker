package se.vandmo.textchecker.maven;

import static com.google.common.base.Charsets.UTF_8;
import static com.google.common.base.Preconditions.checkNotNull;
import com.google.common.io.Files;
import java.io.File;
import java.io.IOException;
import static java.lang.System.lineSeparator;
import java.util.regex.Pattern;
import static java.util.regex.Pattern.compile;
import static se.vandmo.textchecker.maven.ContentType.JAVA;
import static se.vandmo.textchecker.maven.ContentType.TEXT;
import se.vandmo.textchecker.maven.utils.LineChecker;
import se.vandmo.textchecker.maven.utils.LineModifier;


public class Content {
    
    private static final Pattern linePattern = compile("\\r?\\n");
    
    private final ContentType type;
    
    private String data;

    public Content(ContentType type, String data) {
        checkNotNull(type);
        checkNotNull(data);
        this.data = data;
        this.type = type;
    }
    
    public static Content contentFromFile(File file) throws IOException {
        String data = Files.toString(file, UTF_8);
        return new Content(guessType(file), data);
    }
    
    public ContentType type() {
        return type;
    }

    public String data() {
        return data;
    }

    public void data(String data) {
        this.data = data;
    }

    public void modifyLines(LineModifier modifier) {
        StringBuilder result = new StringBuilder();
        for (String line : linePattern.split(data)) {
            result.append(modifier.modify(line)).append(lineSeparator());
        }
        data = result.toString();
    }
    
    public boolean checkLines(LineChecker checker) {
        for (String line : linePattern.split(data)) {
            if (!checker.check(line)) {
                return false;
            }
        }
        return true;
    }

    private static ContentType guessType(File file) {
        if (file.getName().endsWith(".java")) {
            return JAVA;
        }
        return TEXT;
    }

    public void writeTo(File file) throws IOException {
        Files.write(data, file, UTF_8);
    }
    
}
