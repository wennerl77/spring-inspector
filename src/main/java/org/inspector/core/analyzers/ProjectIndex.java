package org.inspector.core.analyzers;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.Expression;
import org.inspector.core.analyzers.data.ClassMetadata;
import org.inspector.core.analyzers.data.ControllerMetadata;
import org.inspector.core.analyzers.data.MethodMetaData;
import org.inspector.core.analyzers.data.ServiceMetadata;
import org.inspector.core.analyzers.endpoints.EndpointMetadata;
import org.inspector.core.analyzers.utils.ValidateAnnotationUtil;

import java.nio.file.Path;
import java.util.*;

public class ProjectIndex {
    private final Map<String, List<ClassOrInterfaceDeclaration>> classesByAnnotation;
    private final Map<String, List<MethodMetaData>> methodsByAnnotation;
    private final Set<ClassMetadata> classes;

    private final Map<String, ControllerMetadata> controllers;
    private final Map<String, ServiceMetadata> services;

    public ProjectIndex() {
        this.classesByAnnotation = new HashMap<>();
        this.methodsByAnnotation = new HashMap<>();
        this.classes = new HashSet<>();
        this.controllers = new HashMap<>();
        this.services = new HashMap<>();
    }

    public void mapClass (String name, Path path, ClassOrInterfaceDeclaration clazz) {
        this.addClass(path, clazz);
        this.addController(name, path, clazz);
        this.addService(name, path, clazz);
    }

    // Métodos de mapeamento de classe - Begin

    private void addController (String name, Path path, ClassOrInterfaceDeclaration clazz) {
        ControllerMetadata controllerMetadata = new ControllerMetadata(clazz, path);

        if (controllerMetadata.isAvaliable()) {
            controllerMetadata.getClazz().getMethods().forEach(method -> {
                EndpointMetadata pseudoEndpoint = new EndpointMetadata(method);
                if (pseudoEndpoint.isAvaliable()) {
                    controllerMetadata.addEndpoint(pseudoEndpoint);
                }
            });
            this.controllers.put(name, controllerMetadata);
        }
    }

    private void addService (String name, Path path, ClassOrInterfaceDeclaration clazz) {
        ServiceMetadata serviceMetadata = new ServiceMetadata(clazz, path);

        if (serviceMetadata.isAvaliable()) {
            this.services.put(name, serviceMetadata);
        }
    }

    private void addClass (Path path, ClassOrInterfaceDeclaration clazz) {
        this.classes.add(new ClassMetadata(path, clazz));
    }

    // Métodos de mapeamento de classe - End

    public Map<String, ControllerMetadata> getControllers() {
        return controllers;
    }

    public Map<String, ServiceMetadata> getServices() {
        return services;
    }

    public void addMethod(String annotation, MethodMetaData methodMetaData) {
        this.methodsByAnnotation.computeIfAbsent(annotation, k -> new ArrayList<>());
        this.methodsByAnnotation.get(annotation).add(methodMetaData);
    }

    public void addClass (String annotation, ClassOrInterfaceDeclaration clazz) {
        this.classesByAnnotation.computeIfAbsent(annotation, k -> new ArrayList<>());
        this.classesByAnnotation.get(annotation).add(clazz);
    }

    public List<ClassOrInterfaceDeclaration> getClassesByAnnotations(String ...anntotations) {
        List<ClassOrInterfaceDeclaration> list = new ArrayList<>();
        for (String annotation : anntotations) {
            if (classesByAnnotation.containsKey(annotation)) list.addAll(classesByAnnotation.get(annotation));
        }
        return list;
    }
}
