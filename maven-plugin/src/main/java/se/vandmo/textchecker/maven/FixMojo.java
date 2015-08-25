package se.vandmo.textchecker.maven;

import com.google.common.io.Files;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;

import static com.google.common.base.Charsets.UTF_8;

@Mojo(
        name = "fix",
        requiresDependencyResolution = ResolutionScope.COMPILE)
public final class FixMojo extends AbstractMojo {
    
    @Parameter(
            defaultValue = "${basedir}",
            required = true,
            readonly = true)
    private File baseFolder;

    private final RulesResolver rulesResolver = new RulesResolver();
    
    private final Checker checker = new Checker(rulesResolver);

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        try {
            FileSupplier fileSupplier = new FileSupplier(baseFolder);
        for (File file : fileSupplier.getFiles()) {
            if (hasComplaints(file)) {
                fix(file);
            }
        }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private void fix(File file) throws IOException {
        String content = Files.toString(file, UTF_8);
        for (Rule rule : rulesResolver.getRulesFor(file)) {
            Fixer fixer = rule.getFixer();
            if (fixer != null) {
                content = fixer.fix(content);
            }
        }
        Files.write(content, file, UTF_8);
    }

    private boolean hasComplaints(File file) throws Exception {
        Collection<Complaint> complaints = checker.getComplaintsFor(file);
        return complaints != null && complaints.size() > 0;
    }
    
}