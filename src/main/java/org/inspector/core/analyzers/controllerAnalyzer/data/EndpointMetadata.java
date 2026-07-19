package org.inspector.core.analyzers.controllerAnalyzer.data;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.Expression;
import org.inspector.core.analyzers.controllerAnalyzer.MetadataAvaliable;
import org.inspector.core.analyzers.controllerAnalyzer.ValidateAnnotationUtil;

import java.util.Optional;
import java.util.Set;

public class EndpointMetadata implements MetadataAvaliable {
    // Tipo de Endpoint
    private EndpointType httpMethod;

    // Endpoint base (do domínio)
    private String classPath;

    // Endpoint do método
    private String methodPath;

    // Método
    private final MethodDeclaration declaration;

    private static final Set<EndpointType> REQUEST_MAPPINGS = Set.of(EndpointType.values());

    public EndpointMetadata(MethodDeclaration methodDeclaration) {
        this.classPath = "";
        this.methodPath = "";
        this.declaration = methodDeclaration;
    }

    public EndpointType getHttpMethod() {
        return httpMethod;
    }

    public String getClassPath() {
        return classPath;
    }

    public String getMethodPath() {
        return methodPath;
    }

    public String getFullPath() {
        return classPath + methodPath;
    }

    public MethodDeclaration getDeclaration() {
        return declaration;
    }

    @Override
    public boolean isAvaliable() {
        for (EndpointType requestMapping : REQUEST_MAPPINGS) {
            if (this.declaration.isAnnotationPresent(requestMapping.getAnnotation())) {
                buildEndpoint(requestMapping);
                return true;
            }
        }

        return false;
    }

    public void setClassPath(String classPath) {
        this.classPath = classPath.replace("\"", "");
    }

    public String getBasicMethodInfo() {
        String className = (String) declaration.findAncestor(TypeDeclaration.class)
                .map(type -> {
                    return  type.getFullyQualifiedName().orElse(type.getNameAsString());
                })
                .orElse("<UnknownClass>");

        String parameters = declaration.getParameters().stream()
                .map(parameter -> parameter.getType().asString())
                .reduce((a, b) -> a + ", " + b)
                .orElse("");

        return "%s#%s(%s)".formatted(
                className,
                declaration.getNameAsString(),
                parameters
        );
    }

    @Override
    public String toString() {
        return """
            EndpointMetadata {
                endpoint    = %s %s%s
                declaration = %s
            }
            """.formatted(
                httpMethod,
                classPath == null ? "" : classPath,
                methodPath == null ? "" : methodPath,
                declaration.getDeclarationAsString(false, false, true)
        );
    }

    private void buildEndpoint (EndpointType requestMapping) {
        Optional<AnnotationExpr> annotationExpr = this.declaration.getAnnotationByName(requestMapping.getAnnotation());
        if (annotationExpr.isEmpty()) return;

        Optional<Expression> optional = ValidateAnnotationUtil.getValueByAnnotation(
                annotationExpr.get(),
                new String[]{"value", "path"});

        this.httpMethod = requestMapping;
        if (optional.isEmpty()) return;
        this.methodPath = optional.get().toString().replace("\"", "");
    }
}
