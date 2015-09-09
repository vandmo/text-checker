package se.vandmo.textchecker.maven;

import static com.google.common.base.Charsets.UTF_8;
import static com.google.common.collect.Lists.newArrayList;
import com.google.common.io.Files;
import java.io.File;
import java.util.Collection;
import java.util.List;
import static se.vandmo.textchecker.maven.ContentType.TEXT;

public final class Checker {
    
    private final RulesResolver rulesResolver;

    public Checker(RulesResolver rulesResolver) {
        this.rulesResolver = rulesResolver;
    }

    public Collection<Complaint> getComplaintsFor(File file) throws Exception {
        List<Complaint> complaints = newArrayList();
        for (Rule rule : rulesResolver.getRulesFor(file)) {
            final String content = Files.toString(file, UTF_8);
            Collection<Complaint> complaintsFromRule = rule.check(new Content(TEXT, content));
            if (complaintsFromRule != null) {
                complaints.addAll(complaintsFromRule);
            }
        }
        return complaints;
    }
    
}