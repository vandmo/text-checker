package se.vandmo.textchecker.maven;

import static se.vandmo.textchecker.maven.Content.contentFromFile;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class Checker {

  private final RulesResolver rulesResolver;

  public Checker(RulesResolver rulesResolver) {
    this.rulesResolver = rulesResolver;
  }

  public Collection<Complaint> getComplaintsFor(File file) throws Exception {
    List<Complaint> complaints = new ArrayList<>();
    Content content = contentFromFile(file);
    for (Rule rule : rulesResolver.getRulesFor(file)) {
      Collection<Complaint> complaintsFromRule = rule.check(content);
      if (complaintsFromRule != null) {
        complaints.addAll(complaintsFromRule);
      }
    }
    return complaints;
  }
}
