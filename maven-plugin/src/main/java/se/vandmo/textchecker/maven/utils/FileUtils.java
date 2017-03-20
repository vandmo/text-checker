package se.vandmo.textchecker.maven.utils;

import static java.nio.file.LinkOption.NOFOLLOW_LINKS;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFilePermission;
import java.util.HashSet;
import java.util.Set;

import com.google.common.collect.ImmutableSet;

public final class FileUtils {
  private FileUtils() {}

  private static final ImmutableSet<PosixFilePermission> EXECUTABLE_PERMISSIONS = ImmutableSet.of(
      PosixFilePermission.OTHERS_EXECUTE,
      PosixFilePermission.GROUP_EXECUTE,
      PosixFilePermission.OWNER_EXECUTE);

  public static boolean isExecutable(Path file) throws IOException {
    Set<PosixFilePermission> intersection = new HashSet<>(Files.getPosixFilePermissions(file, NOFOLLOW_LINKS));
    intersection.retainAll(EXECUTABLE_PERMISSIONS);
    return !intersection.isEmpty();
  }

  public static void setExecutable(Path file, boolean executable) throws IOException {
    Set<PosixFilePermission> permissions = Files.getPosixFilePermissions(file, NOFOLLOW_LINKS);
    if (executable) {
      permissions.addAll(EXECUTABLE_PERMISSIONS);
    } else {
      permissions.removeAll(EXECUTABLE_PERMISSIONS);
    }
    Files.setPosixFilePermissions(file, permissions);
  }

}
