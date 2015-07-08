package se.vandmo.textchecker.maven;

import java.io.File;
import java.util.Collection;
import java.util.List;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.Arrays.asList;
import static org.apache.commons.io.FileUtils.listFiles;

@Mojo(
        name = "check",
        defaultPhase = LifecyclePhase.GENERATE_SOURCES,
        requiresDependencyResolution = ResolutionScope.COMPILE)
public final class CheckMojo extends AbstractMojo {
    
    @Component
    private MavenProject project;
    
    @Parameter(
            defaultValue = "${basedir}",
            required = true,
            readonly = true)
    private File baseFolder;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        List<Complaint> complaints = newArrayList();
        for (File file : getFiles()) {
            complaints.addAll(getComplaintsFor(file));
        }
        if (!complaints.isEmpty()) {
            logAndFail(complaints);
        }
    }

    private Iterable<File> getFiles() {
        return listFiles(baseFolder, new SuffixFileFilter(asList(
                ".java", ".txt", ".xml", ".js", ".html")),
                new IOFileFilter() {
                    public boolean accept(File file) {
                        return !file.isDirectory() || "target".equals(file.getName());
                    }
                    public boolean accept(File file, String string) {
                        return true;
                    }
                });
    }

    private Collection<? extends Complaint> getComplaintsFor(File file) {
        List<Complaint> complaints = newArrayList();
        for (Rule rule : getRulesFor(file)) {
            Collection<Complaint> complaintsFromRule = rule.check(file);
            if (complaintsFromRule != null) {
                complaints.addAll(complaintsFromRule);
            }
        }
        return complaints;
    }

    private Iterable<Rule> getRulesFor(File file) {
        return asList((Rule)new NoTabsRule());
    }

    private void logAndFail(List<Complaint> complaints) throws MojoFailureException {
        log(complaints);
        throw new MojoFailureException(
                "One or more rules was broken. Try running mvn se.vandmo.text-checker:text-checker:fix to fix the complaints.");
    }

    private void log(List<Complaint> complaints) {
        for (Complaint complaint : complaints) {
            getLog().error(complaint.getMessage());
        }
    }
    
}