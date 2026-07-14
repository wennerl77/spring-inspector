package org.inspector.core.issues;

import org.jspecify.annotations.NonNull;

import java.nio.file.Path;

public record Issue(
        Severity severity,
        IssueType type,
        String name,
        String description,
        Integer line,
        Path path,
        String suggestion

) {
    @Override
    @NonNull
    public String toString() {
        return """
            [%s] %s
            Name       : %s
            Description: %s
            File       : %s
            Line       : %s
            Suggestion : %s
            """
                .formatted(
                        severity,
                        type,
                        name,
                        description,
                        path != null ? path : "-",
                        line != null ? line : "-",
                        suggestion != null ? suggestion : "-"
                );
    }
}
