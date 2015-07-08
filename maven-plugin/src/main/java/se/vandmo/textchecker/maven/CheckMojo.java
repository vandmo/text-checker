package se.vandmo.textchecker.maven;

import com.google.common.io.Files;
import java.io.File;
import java.util.Collection;
import java.util.List;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;

import static com.google.common.base.Charsets.UTF_8;
import static com.google.common.collect.Collections2.transform;
import static com.google.common.collect.Lists.newArrayList;
import static java.util.Arrays.asList;
import static org.apache.commons.io.FileUtils.listFiles;

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

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        List<ComplaintWithFileInfo> complaints = newArrayList();
        try {
            addComplaintsForFiles(complaints);
        } catch (Exception ex) {
            throw new MojoExecutionException("Failure", ex);
        }
        if (!complaints.isEmpty()) {
            logAndFail(complaints);
        }
    }

    private void addComplaintsForFiles(List<ComplaintWithFileInfo> complaints) throws Exception {
        for (File file : getFiles()) {
            complaints.addAll(getComplaintsFor(file));
        }
    }

    private Iterable<File> getFiles() {
        return listFiles(baseFolder,
                new SuffixFileFilter(
                        asList(".java", ".txt", ".xml", ".js", ".html")),
                new IOFileFilter() {
                    @Override
                    public boolean accept(File file) {
                        return !file.isDirectory() || "target".equals(file.getName());
                    }
                    @Override
                    public boolean accept(File file, String string) {
                        return true;
                    }
                });
    }

    private Collection<ComplaintWithFileInfo> getComplaintsFor(File file) throws Exception {
        List<ComplaintWithFileInfo> complaints = newArrayList();
        for (Rule rule : getRulesFor(file)) {
            final String content = Files.toString(file, UTF_8);
            Collection<Complaint> complaintsFromRule = rule.check(content);
            if (complaintsFromRule != null) {
                Collection<ComplaintWithFileInfo> complaintsWithFile = transform(complaintsFromRule,
                    complaint -> new ComplaintWithFileInfo(complaint, relativeFileNameFor(file)));
                complaints.addAll(complaintsWithFile);
            }
        }
        return complaints;
    }

    private Iterable<Rule> getRulesFor(File file) {
        return asList(new NoTabsRule());
    }

    private void logAndFail(List<ComplaintWithFileInfo> complaints) throws MojoFailureException {
        log(complaints);
        throw new MojoFailureException(
                "One or more rules was broken. Try running mvn se.vandmo.text-checker:text-checker-maven-plugin:fix to fix the complaints.");
    }

    private void log(List<ComplaintWithFileInfo> complaints) {
        for (ComplaintWithFileInfo complaintWithFile : complaints) {
            final String filePath = complaintWithFile.getFileName();
            getLog().error(filePath+" "+complaintWithFile.getComplaint().getMessage());
        }
    }

    private String relativeFileNameFor(File file) {
        return file.getAbsolutePath().substring(baseFolder.getAbsolutePath().length()+1);
    }
    
}