package se.vandmo.textchecker.maven;

import static java.util.Arrays.asList;
import static org.apache.commons.io.FileUtils.listFiles;

import java.io.File;
import java.util.Collection;

import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.SuffixFileFilter;

import com.google.common.collect.ImmutableSet;


public final class FileSupplier {

  private static final ImmutableSet<String> EXCLUDES = ImmutableSet.of("target", ".git", ".junk", ".m2");

  private final File baseFolder;

  public FileSupplier(File baseFolder) {
    this.baseFolder = baseFolder;
  }

  public Collection<File> getFiles() {
    return listFiles(baseFolder,
      new SuffixFileFilter(
        asList(".java", ".txt", ".xml", ".js", ".html")),
      new IOFileFilter() {
        @Override
        public boolean accept(File dir) {
          return dir.isDirectory() && !EXCLUDES.contains(dir.getName());
        }

        @Override
        public boolean accept(File dir, String name) {
          return true;
        }
      });
  }

  public String relativeFileNameFor(File file) {
    return file.getAbsolutePath().substring(baseFolder.getAbsolutePath().length() + 1);
  }

}
