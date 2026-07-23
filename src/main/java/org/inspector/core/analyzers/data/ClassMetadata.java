package org.inspector.core.analyzers.data;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;

import java.nio.file.Path;
import java.util.Objects;

public record ClassMetadata (
        Path path,
        ClassOrInterfaceDeclaration clazz
) {
    @Override
    public int hashCode() {
        return path.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ClassMetadata that = (ClassMetadata) o;
        return Objects.equals(this.hashCode(), that.hashCode());
    }
}
