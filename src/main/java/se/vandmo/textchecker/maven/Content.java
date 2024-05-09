package se.vandmo.textchecker.maven;

import static java.lang.System.lineSeparator;
import static java.util.Objects.requireNonNull;
import static java.util.regex.Pattern.compile;
import static se.vandmo.textchecker.maven.ContentType.JAVA;
import static se.vandmo.textchecker.maven.ContentType.TEXT;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.regex.Pattern;
import se.vandmo.textchecker.maven.utils.LineChecker;
import se.vandmo.textchecker.maven.utils.LineModifier;

public final class Content {

  private static final Pattern LINE_PATTERN = compile("\\r?\\n");

  private final ContentType type;
  private String data;

  public Content(ContentType type, String data) {
    requireNonNull(type);
    requireNonNull(data);
    this.data = data;
    this.type = type;
  }

  public static Content contentFromFile(File file) throws IOException {
    String data = new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
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
    for (String line : LINE_PATTERN.split(data)) {
      result.append(modifier.modify(line)).append(lineSeparator());
    }
    data = result.toString();
  }

  public boolean checkLines(LineChecker checker) {
    for (String line : LINE_PATTERN.split(data)) {
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
    Files.write(file.toPath(), data.getBytes(StandardCharsets.UTF_8));
  }
}
