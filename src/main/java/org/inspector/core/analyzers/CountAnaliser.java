package org.inspector.core.analyzers;

import org.inspector.core.issues.Issue;
import org.inspector.core.issues.IssueType;
import org.inspector.core.issues.Severity;

import java.util.List;
import java.util.stream.Stream;

public class CountAnaliser implements Analyzer {

    private final ProjectIndex projectIndex = CodebaseAnalyzer.getProjectIndex();

    private Integer getNumbersAnnotations (String annotation) {
        return projectIndex.getAnnotationsOfClass(annotation) == null ? 0 : projectIndex.getAnnotationsOfClass(annotation).size();
    }

    @Override
    public List<Issue> analyze() {
        return Stream.of(IssueType.values())
                .map(issueType -> new Issue(
                        Severity.INFO,
                        issueType,
                        issueType.getName(),
                        "Count: " + getNumbersAnnotations(issueType.getAnnotation()),
                        null,
                        null,
                        null))
                .toList();
    }
}
