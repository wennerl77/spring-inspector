package org.inspector.core.analyzers.data;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;

public class MethodMetaData {
    private final ClassOrInterfaceDeclaration owner;
    private final MethodDeclaration method;

    public MethodMetaData(ClassOrInterfaceDeclaration owner, MethodDeclaration method) {
        this.owner = owner;
        this.method = method;
    }

    public ClassOrInterfaceDeclaration getOwner() {
        return owner;
    }

    public MethodDeclaration getMethod() {
        return method;
    }
}
