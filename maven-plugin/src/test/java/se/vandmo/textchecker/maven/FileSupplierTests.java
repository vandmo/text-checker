package se.vandmo.textchecker.maven;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toSet;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import org.junit.Test;

public final class FileSupplierTests {

  private static final Path ROOT = Paths.get("src", "test", "data", "file-supplier");

  @Test
  public void defaultExcludes() throws IOException {
    Path testCaseRoot = ROOT.resolve("default-excludes");
    FileSupplier supplier = new FileSupplier(testCaseRoot, emptyList(), true);
    Set<String> matchedFiles = supplier
        .getFiles()
        .stream()
        .map(file -> testCaseRoot.relativize(file.toPath()).toString())
        .collect(toSet());
    assertEquals(new HashSet<>(asList(
        "included.html",
        "included.txt",
        "folder/included.yaml")), matchedFiles);
  }
}
