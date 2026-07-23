package org.inspector.core.analyzers.data;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ServiceMetadata implements MetadataAvaliable {

    private ClassOrInterfaceDeclaration clazz;
    private Path path;
    private List<MethodDeclaration> methods;

    public ServiceMetadata(ClassOrInterfaceDeclaration clazz, Path path) {
        this.clazz = clazz;
        this.path = path;
        this.methods = new ArrayList<>();
    }

    public void addMethod (MethodDeclaration method) {
        if (isAvaliable()) this.methods.add(method);
    }

    @Override
    public boolean isAvaliable() {
        return clazz.isAnnotationPresent("Service");
    }
}
