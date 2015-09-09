package se.vandmo.textchecker.maven;

import com.google.common.base.Preconditions;
import static java.lang.System.lineSeparator;
import java.util.regex.Pattern;
import se.vandmo.textchecker.maven.utils.LineChecker;
import se.vandmo.textchecker.maven.utils.LineModifier;


/**
 * Mutable and NOT thread safe.
 */
public final class Content {
    
    private static final Pattern linePattern = Pattern.compile("\\r?\\n");
    
    private final ContentType type;
    
    private String data;

    public Content(ContentType type, String data) {
        Preconditions.checkNotNull(type);
        Preconditions.checkNotNull(data);
        this.data = data;
        this.type = type;
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

    public String modifyLines(LineModifier modifier) {
        StringBuilder result = new StringBuilder();
        for (String line : linePattern.split(data)) {
            result.append(modifier.modify(line)).append(lineSeparator());
        }
        return result.toString();
    }
    
    public boolean checkLines(LineChecker checker) {
        for (String line : linePattern.split(data)) {
            if (!checker.check(line)) {
                return false;
            }
        }
        return true;
    }
    
}
