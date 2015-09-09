package se.vandmo.textchecker.maven;

import static com.google.common.collect.Lists.newArrayList;
import java.io.File;
import java.util.Collection;
import java.util.List;
import static se.vandmo.textchecker.maven.Content.contentFromFile;

public final class Checker {
    
    private final RulesResolver rulesResolver;

    public Checker(RulesResolver rulesResolver) {
        this.rulesResolver = rulesResolver;
    }

    public Collection<Complaint> getComplaintsFor(File file) throws Exception {
        List<Complaint> complaints = newArrayList();
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