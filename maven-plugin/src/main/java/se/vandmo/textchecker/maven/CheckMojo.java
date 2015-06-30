package se.vandmo.textchecker.maven;

import java.io.File;
import java.util.List;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;

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
    public void execute() throws MojoExecutionException, MojoFailureException {}
    
}