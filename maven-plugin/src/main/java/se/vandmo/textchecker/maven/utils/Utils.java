package se.vandmo.textchecker.maven.utils;

import static java.lang.System.lineSeparator;
import java.util.regex.Pattern;


public final class Utils {
    private Utils() {}

    private static final Pattern linePattern = Pattern.compile("\\r?\\n");
    
    public static String modifyLines(String content, LineModifier modifier) {
        StringBuilder result = new StringBuilder();
        for (String line : linePattern.split(content)) {
            result.append(modifier.modify(line)).append(lineSeparator());
        }
        return result.toString();
    }
    
    public static boolean allLinesAreOk(String content, LineChecker checker) {
        for (String line : linePattern.split(content)) {
            if (!checker.check(line)) {
                return false;
            }
        }
        return true;
    }

}
