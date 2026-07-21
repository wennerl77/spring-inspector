package org.inspector.core.analyzers;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.Expression;
import org.inspector.core.analyzers.data.ControllerMetadata;
import org.inspector.core.analyzers.data.MethodMetaData;
import org.inspector.core.analyzers.endpoints.EndpointMetadata;
import org.inspector.core.analyzers.utils.ValidateAnnotationUtil;

import java.nio.file.Path;
import java.util.*;

public class ProjectIndex {
    private final Map<String, List<ClassOrInterfaceDeclaration>> classesByAnnotation;
    private final Map<String, List<MethodMetaData>> methodsByAnnotation;
    private final List<ClassOrInterfaceDeclaration> classes;

    private final Map<String, ControllerMetadata> controllers;

    public ProjectIndex() {
        this.classesByAnnotation = new HashMap<>();
        this.methodsByAnnotation = new HashMap<>();
        this.classes = new ArrayList<>();
        this.controllers = new HashMap<>();
    }

    public void addController (String name, Path path, ClassOrInterfaceDeclaration clazz) {
        ControllerMetadata controllerMetadata = new ControllerMetadata(clazz, path);

        if (controllerMetadata.isAvaliable()) {
            controllerMetadata.getClazz().getMethods().forEach(method -> {
                EndpointMetadata pseudoEndpoint = new EndpointMetadata(method);
                if (pseudoEndpoint.isAvaliable()) {
                    Optional<AnnotationExpr> annotationExpr = controllerMetadata.getClazz().getAnnotationByName("RequestMapping");
                    Optional<Expression> expression = Optional.empty();
                    if (annotationExpr.isPresent()) {
                        expression = ValidateAnnotationUtil.getValueByAnnotation(
                                annotationExpr.get(),
                                new String[]{"value", "path"}
                        );
                    }

                    // Adiciona Endpoint
                    expression.ifPresent(value -> {
                        pseudoEndpoint.setClassPath(value.toString());
                        pseudoEndpoint.setOwner(controllerMetadata);
                    });
                    controllerMetadata.addEndpoint(pseudoEndpoint);
                }
            });
            this.controllers.put(name, controllerMetadata);
        }
    }

    public Map<String, ControllerMetadata> getControllers() {
        return controllers;
    }

    public void addMethod(String annotation, MethodMetaData methodMetaData) {
        this.methodsByAnnotation.computeIfAbsent(annotation, k -> new ArrayList<>());
        this.methodsByAnnotation.get(annotation).add(methodMetaData);
    }

    public void addClass(String annotation, ClassOrInterfaceDeclaration clazz) {
        this.classesByAnnotation.computeIfAbsent(annotation, k -> new ArrayList<>());
        this.classesByAnnotation.get(annotation).add(clazz);
    }

    public void addClass(ClassOrInterfaceDeclaration clazz) {
        this.classes.add(clazz);
    }

    public List<ClassOrInterfaceDeclaration> getClassesByAnnotations(String ...anntotations) {
        List<ClassOrInterfaceDeclaration> list = new ArrayList<>();
        for (String annotation : anntotations) {
            if (classesByAnnotation.containsKey(annotation)) list.addAll(classesByAnnotation.get(annotation));
        }
        return list;
    }

    public List<MethodMetaData> getAnnotationsOfMethod (String annotation) {
        return methodsByAnnotation.get(annotation);
    }

    public List<ClassOrInterfaceDeclaration> getClasses() {
        return classes;
    }
}
