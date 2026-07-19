package org.inspector.core.analyzers;

import org.inspector.core.issues.Issue;

import java.util.List;

public interface Analyzer {
    List<Issue> analyze();
    default void sendReport() {
        printLine();
        System.out.println();
        analyze().forEach(System.out::println);
        printLine();
    }
    default void printLine() {
        System.out.println("=".repeat(50));
    }
}
