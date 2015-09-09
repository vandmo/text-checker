package se.vandmo.textchecker.maven;

import java.io.File;
import static java.util.Arrays.asList;
import java.util.Collection;
import static org.apache.commons.io.FileUtils.listFiles;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.SuffixFileFilter;


public final class FileSupplier {
    
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
                    public boolean accept(File file) {
                        return file.isDirectory() && !"target".equals(file.getName());
                    }
                    @Override
                    public boolean accept(File file, String string) {
                        return true;
                    }
                });
    }

    public String relativeFileNameFor(File file) {
        return file.getAbsolutePath().substring(baseFolder.getAbsolutePath().length()+1);
    }
   
}