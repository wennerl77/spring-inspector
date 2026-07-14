package org.inspector.core.analyzers;

import org.inspector.core.issues.Issue;
import org.inspector.core.issues.IssueType;
import org.inspector.core.issues.Severity;

import java.util.List;

public class CountAnaliser implements Analyzer {

    private final ProjectIndex projectIndex = CodebaseAnalyzer.getProjectIndex();

    public Issue countControllers () {
        return new Issue(
                Severity.INFO,
                IssueType.CONTROLLER,
                "Controller counts",
                "Number of Controllers: " + getNumbersAnnotations("RestController"),
                null,
                null,
                null
        );
    }

    public Issue countServices () {
        return new Issue(
                Severity.INFO,
                IssueType.SERVICE,
                "Service counts",
                "Number of Services: " + getNumbersAnnotations("Service"),
                null,
                null,
                null
        );
    }

    public Issue countMappers () {
        return new Issue(
                Severity.INFO,
                IssueType.MAPPER,
                "Mapper counts",
                "Number of Mappers: " + getNumbersAnnotations("Mapper"),
                null,
                null,
                null
        );
    }

    public Issue countRepositories () {
        return new Issue(
                Severity.INFO,
                IssueType.REPOSITORY,
                "Repository counts",
                "Number of Repositories: " + getNumbersAnnotations("Repository"),
                null,
                null,
                null
        );
    }

    private Integer getNumbersAnnotations (String annotation) {
        return projectIndex.getAnnotationsOfClass(annotation) == null ? 0 : projectIndex.getAnnotationsOfClass(annotation).size();
    }

    @Override
    public List<Issue> analyze() {
        return List.of(countControllers(), countServices(), countMappers(), countRepositories());
    }
}
