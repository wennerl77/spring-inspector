package org.inspector.core.analyzers.controllerAnalyzer.data;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import org.inspector.core.analyzers.controllerAnalyzer.MetadataAvaliable;

import java.util.ArrayList;
import java.util.List;

public class ControllerMetadata implements MetadataAvaliable{
    private ClassOrInterfaceDeclaration clazz;
    private List<EndpointMetadata> endpoints;

    public ControllerMetadata(ClassOrInterfaceDeclaration clazz) {
        this.clazz = clazz;
        this.endpoints = new ArrayList<>();
    }

    public void addEndpoint (EndpointMetadata endpointMetadata) {
        this.endpoints.add(endpointMetadata);
    }

    public ClassOrInterfaceDeclaration getClazz() {
        return clazz;
    }

    public List<EndpointMetadata> getEndpoints() {
        return endpoints;
    }

    @Override
    public boolean isAvaliable() {
        return this.clazz.isAnnotationPresent("Controller") ||
                this.clazz.isAnnotationPresent("RestController");
    }
}
