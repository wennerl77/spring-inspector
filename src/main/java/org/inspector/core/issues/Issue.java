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
        StringBuilder sb = new StringBuilder();

        sb.append("[%s] %s%n".formatted(severity, type));

        if (name != null) {
            sb.append("Name       : ").append(name).append('\n');
        }

        if (description != null) {
            sb.append("Description: ").append(description).append('\n');
        }

        if (path != null) {
            sb.append("File       : ").append(path).append('\n');
        }

        if (line != null) {
            sb.append("Line       : ").append(line).append('\n');
        }

        if (suggestion != null) {
            sb.append("Suggestion : ").append(suggestion).append('\n');
        }

        return sb.toString().stripTrailing().concat("\n");
    }
}
