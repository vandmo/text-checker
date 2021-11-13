package se.vandmo.textchecker.maven.utils;

import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;
import static java.util.Collections.unmodifiableSet;
import static org.apache.commons.lang3.StringUtils.removeEnd;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class PathMatchers {
  private PathMatchers() {}

  private static boolean anyMatches(Path path, Collection<PathMatcher> matchers) {
    for (PathMatcher matcher : matchers) {
      if (matcher.matches(path)) {
        return true;
      }
    }
    return false;
  }

  public static PathMatcher ofEitherGlob(Collection<String> globs) {
    List<PathMatcher> matchers =
        unmodifiableList(globs
                .stream()
                .map(glob -> globMatcher(glob))
                .collect(Collectors.toList()));
    return path -> anyMatches(path, matchers);
  }

  private static PathMatcher globMatcher(String glob) {
    if (glob.endsWith("/")) {
      PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:" + removeEnd(glob, "/"));
      return path -> {
        if (Files.isDirectory(path)) {
          return matcher.matches(path);
        }
        return false;
      };
    }
    return FileSystems.getDefault().getPathMatcher("glob:" + glob);
  }

  public static PathMatcher any(PathMatcher... matcher) {
    return any(asList(matcher));
  }

  public static PathMatcher any(List<PathMatcher> matchers) {
    List<PathMatcher> copiedMatchersList = unmodifiableList(new ArrayList<>(matchers));
    return path -> anyMatches(path, copiedMatchersList);
  }

  public static PathMatcher endsWithAny(Collection<String> suffixes) {
    List<String> copiedSuffixes = unmodifiableList(new ArrayList<>(suffixes));
    return path -> endsWithAny(path.toString(), copiedSuffixes);
  }

  public static PathMatcher anyName(Collection<String> names) {
    Set<String> copiedNames = unmodifiableSet(new HashSet<>(names));
    return path -> copiedNames.contains(path.getFileName().toString());
  }

  public static PathMatcher anyFolder(Collection<String> names) {
    Set<String> copiedNames = unmodifiableSet(new HashSet<>(names));
    return path -> Files.isDirectory(path) && copiedNames.contains(path.getFileName().toString());
  }

  public static PathMatcher relativized(Path base, PathMatcher matcher) {
    return path -> matcher.matches(base.relativize(path));
  }

  private static boolean endsWithAny(String str, Iterable<String> suffixes) {
    for (String suffix : suffixes) {
      if (str.endsWith(suffix)) {
        return true;
      }
    }
    return false;
  }

}
