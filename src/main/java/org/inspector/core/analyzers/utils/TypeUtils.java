package org.inspector.core.analyzers.utils;

import com.github.javaparser.ast.type.ClassOrInterfaceType;
import org.inspector.core.analyzers.CodebaseAnalyzer;
import org.inspector.core.analyzers.ProjectIndex;

public class TypeUtils {

    private static final ProjectIndex PROJECT_INDEX = CodebaseAnalyzer.getProjectIndex();
    public static boolean primitivePackage (ClassOrInterfaceType classType) {
        return false;
    }
}
