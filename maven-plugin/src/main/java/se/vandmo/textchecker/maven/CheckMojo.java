package se.vandmo.textchecker.maven;

import static com.google.common.collect.Collections2.transform;
import static com.google.common.collect.Lists.newArrayList;
import static java.util.Collections.emptyList;

import java.io.File;
import java.util.Collection;
import java.util.List;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;


@Mojo(
  name = "check",
  defaultPhase = LifecyclePhase.GENERATE_SOURCES,
  requiresDependencyResolution = ResolutionScope.COMPILE)
public final class CheckMojo extends AbstractMojo {

  @Parameter(
    defaultValue = "${basedir}",
    required = true,
    readonly = true)
  private File baseFolder;

  @Parameter
  private List<String> excludes;

  @Parameter(defaultValue = "true")
  private boolean useDefaultExcludes;

  private final Checker checker = new Checker(new RulesResolver());

  @Override
  public void execute() throws MojoExecutionException, MojoFailureException {
    List<ComplaintWithFileInfo> complaints = newArrayList();
    try {
      addComplaintsForFiles(complaints);
    } catch (Exception ex) {
      throw new RuntimeException(ex);
    }
    if (!complaints.isEmpty()) {
      logAndFail(complaints);
    }
  }

  private void addComplaintsForFiles(List<ComplaintWithFileInfo> complaints) throws Exception {
    FileSupplier fileSupplier = new FileSupplier(
        baseFolder.toPath(),
        excludes == null ? emptyList() : excludes,
        useDefaultExcludes);
    for (File file : fileSupplier.getFiles()) {
      complaints.addAll(getComplaintsFor(file, fileSupplier));
    }
  }

  private Collection<ComplaintWithFileInfo> getComplaintsFor(File file, FileSupplier fileNameResolver) throws Exception {
    return transform(checker.getComplaintsFor(file),
      complaint -> new ComplaintWithFileInfo(complaint, fileNameResolver.relativeFileNameFor(file)));
  }

  private void logAndFail(List<ComplaintWithFileInfo> complaints) throws MojoFailureException {
    log(complaints);
    throw new MojoFailureException(
      "One or more rules was broken. Try running \"mvn se.vandmo:text-checker-maven-plugin:fix\" to fix the complaints.");
  }

  private void log(List<ComplaintWithFileInfo> complaints) {
    for (ComplaintWithFileInfo complaintWithFile : complaints) {
      final String filePath = complaintWithFile.getFileName();
      getLog().error(filePath + " " + complaintWithFile.getComplaint().getMessage());
    }
  }

}
