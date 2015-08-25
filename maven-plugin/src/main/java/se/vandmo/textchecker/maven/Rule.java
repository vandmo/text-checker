package se.vandmo.textchecker.maven;

import java.util.Collection;


public interface Rule {

    Collection<Complaint> check(String content);
    Fixer getFixer();

}
