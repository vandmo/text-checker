package se.vandmo.textchecker.maven;

import java.io.File;
import java.util.Collection;


public interface Rule {

    Collection<Complaint> check(File file) throws Exception;
    Fixer getFixer();

}
