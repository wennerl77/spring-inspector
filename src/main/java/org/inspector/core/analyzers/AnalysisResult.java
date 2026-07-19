package org.inspector.core.analyzers;

import org.inspector.core.issues.Issue;

import java.time.Duration;
import java.util.List;

public record AnalysisResult (
        String name,
        List<Issue> issues,
        Duration duration
) {
}
