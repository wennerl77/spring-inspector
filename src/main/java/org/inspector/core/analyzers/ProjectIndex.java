package org.inspector.core.analyzers;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProjectIndex {
    private final Map<String, List<ClassOrInterfaceDeclaration>> classesByAnnotation;
    private final Map<String, List<MethodMetaData>> methodsByAnnotation;

    public ProjectIndex(Map<String, List<ClassOrInterfaceDeclaration>> classesByAnnotation, Map<String, List<MethodMetaData>> methodsByAnnotation) {
        this.classesByAnnotation = classesByAnnotation;
        this.methodsByAnnotation = methodsByAnnotation;
    }

    public ProjectIndex() {
        this.classesByAnnotation = new HashMap<>();
        this.methodsByAnnotation = new HashMap<>();
    }

    public void addMethod(String annotation, MethodMetaData methodMetaData) {
        this.methodsByAnnotation.computeIfAbsent(annotation, k -> new ArrayList<>());
        this.methodsByAnnotation.get(annotation).add(methodMetaData);
    }

    public void addClass(String annotation, ClassOrInterfaceDeclaration clazz) {
        this.classesByAnnotation.computeIfAbsent(annotation, k -> new ArrayList<>());
        this.classesByAnnotation.get(annotation).add(clazz);
    }

    public List<ClassOrInterfaceDeclaration> getAnnotationsOfClass(String anntotation) {
        return classesByAnnotation.get(anntotation);
    }

    public List<MethodMetaData> getAnnotationsOfMethod (String annotation) {
        return methodsByAnnotation.get(annotation);
    }
}
