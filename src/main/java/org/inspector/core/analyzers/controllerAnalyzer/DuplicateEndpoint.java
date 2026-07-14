package org.inspector.core.analyzers.controllerAnalyzer;

import org.inspector.core.analyzers.Analyzer;
import org.inspector.core.issues.Issue;

import java.util.List;

public class DuplicateEndpoint implements Analyzer {

    @Override
    public List<Issue> analyze() {
        return List.of();
    }
}
