package se.vandmo.textchecker.maven;

import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;
import static se.vandmo.textchecker.maven.utils.PathMatchers.any;
import static se.vandmo.textchecker.maven.utils.PathMatchers.anyFolder;
import static se.vandmo.textchecker.maven.utils.PathMatchers.endsWithAny;
import static se.vandmo.textchecker.maven.utils.PathMatchers.ofEitherGlob;
import static se.vandmo.textchecker.maven.utils.PathMatchers.relativized;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class FileSupplier {

  private static final List<String> DEFAULT_EXCLUDED_FOLDERS =
      asList("target", ".git", ".junk", ".m2");
  private static final Collection<String> TEXT_FILE_SUFFIXES =
      asList(".java", ".txt", ".xml", ".js", ".html", ".md", ".adoc", ".yml", ".yaml", ".json");

  private final Path base;
  private final PathMatcher excludesMatcher;
  private final PathMatcher suffixMatcher;

  public FileSupplier(Path base, List<String> excludes, boolean useDefaultExcludes) {
    this.base = base;
    excludesMatcher = excludesMatcher(this.base, excludes, useDefaultExcludes);
    suffixMatcher = relativized(base, endsWithAny(TEXT_FILE_SUFFIXES));
  }

  private static PathMatcher excludesMatcher(
      Path base, List<String> excludes, boolean useDefaultExcludes) {
    PathMatcher excludesMatcher = relativized(base, ofEitherGlob(excludes));
    if (useDefaultExcludes) {
      return any(excludesMatcher, anyFolder(DEFAULT_EXCLUDED_FOLDERS));
    } else {
      return excludesMatcher;
    }
  }

  public List<File> getFiles() {
    List<File> result = new ArrayList<>();
    try {
      Files.walkFileTree(
          base,
          new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
                throws IOException {
              if (excludesMatcher.matches(dir)) {
                return FileVisitResult.SKIP_SUBTREE;
              }
              return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path path, BasicFileAttributes attrs)
                throws IOException {
              if (excludesMatcher.matches(path)) {
                return FileVisitResult.CONTINUE;
              }
              if (suffixMatcher.matches(path)) {
                result.add(path.toFile());
              }
              return FileVisitResult.CONTINUE;
            }
          });
    } catch (IOException ex) {
      throw new UncheckedIOException(ex);
    }
    return unmodifiableList(result);
  }

  public String relativeFileNameFor(File file) {
    return base.relativize(file.toPath()).toString();
  }
}
