package org.inspector.core.reports;

import org.inspector.core.analyzers.Analyzer;
import org.inspector.core.analyzers.CountAnaliser;

import java.util.ArrayList;
import java.util.List;

public class Report {
    private final List<Analyzer> analyzers;

    public Report () {
        analyzers = new ArrayList<>();
        analyzers.add(new CountAnaliser());
    }

    public void sendReportCount () {
        printLine();
        System.out.println();
        analyzers.forEach(analyzer -> {
            analyzer.analyze().forEach(System.out::println);
        });
        printLine();
    }

    private void printLine() {
        System.out.println("=".repeat(50));
    }
}
