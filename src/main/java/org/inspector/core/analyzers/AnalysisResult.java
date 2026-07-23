package org.inspector.core.analyzers;

import org.inspector.core.report.Issue;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

public class AnalysisResult {

    String name;
    List<Issue> issues;
    Duration duration;

    public AnalysisResult(String name, Analyzer analyzer) {
        Instant initial = Instant.now();
        this.name = name;
        this.issues = analyzer.analyze();
        this.duration = Duration.between(initial, Instant.now());
    }

    public void print() {
        printLine("=", 50);
        System.out.println("\tAnalysis: " + this.name);
        printLine("=", 50);
        sendReport(this.issues);
        printLine("=", 50);
        System.out.println("\tDuration: " + this.duration.toMillis() + " ms");
        printLine("=", 50);

        System.out.println();
    }

    public void sendReport(List<Issue> issues) {
        issues.forEach(System.out::println);
    }
    public void printLine(String caracter, int repeat) {
        System.out.println(caracter.repeat(repeat));
    }
}
