package org.inspector.core.analyzers;

import org.inspector.core.report.Issue;

import java.util.List;

public interface Analyzer {
    List<Issue> analyze();
}
