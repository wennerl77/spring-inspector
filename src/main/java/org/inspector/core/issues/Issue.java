package org.inspector.core.issues;

import org.jspecify.annotations.NonNull;

import java.nio.file.Path;

public record Issue(
        Severity severity,
        IssueType issueType,
        BeanType beanType,
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

        sb.append("[%s] %s%n".formatted(severity, issueType == null ? "" : issueType));

        if (beanType != null) {
            sb.append("Bean        : ")
                    .append(beanType.getName())
                    .append('\n');
        }

        if (name != null) {
            sb.append("Name        : ")
                    .append(name)
                    .append('\n');
        }

        if (description != null) {
            sb.append("Description : ");

            String indented = description.replace("\n", "\n              ");
            sb.append(indented).append('\n');
        }

        if (path != null) {
            sb.append("File        : ")
                    .append(path)
                    .append('\n');
        }

        if (line != null) {
            sb.append("Line        : ")
                    .append(line)
                    .append('\n');
        }

        if (suggestion != null) {
            sb.append("Suggestion  : ")
                    .append(suggestion)
                    .append('\n');
        }

        return sb.toString().stripTrailing().concat("\n");
    }
}
