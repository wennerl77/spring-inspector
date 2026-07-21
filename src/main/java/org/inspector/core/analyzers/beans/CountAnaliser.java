package org.inspector.core.analyzers.beans;

import org.inspector.core.analyzers.Analyzer;
import org.inspector.core.analyzers.CodebaseAnalyzer;
import org.inspector.core.analyzers.ProjectIndex;
import org.inspector.core.report.Issue;
import org.inspector.core.report.BeanType;
import org.inspector.core.report.Severity;

import java.util.List;
import java.util.stream.Stream;

public class CountAnaliser implements Analyzer {

    private final ProjectIndex projectIndex = CodebaseAnalyzer.getProjectIndex();

    private Integer getNumbersAnnotations(String ...annotation) {
        return projectIndex.getClassesByAnnotations(annotation) == null ? 0 : projectIndex.getClassesByAnnotations(annotation).size();
    }

    @Override
    public List<Issue> analyze() {
        return Stream.of(BeanType.values())
                .map(beanType -> new Issue(
                        Severity.INFO,
                        null,
                        beanType,
                        "Counting of " + beanType.getName(),
                        "Count: " + getNumbersAnnotations(beanType.getAnnotations()),
                        null,
                        null,
                        null))
                .toList();
    }
}
