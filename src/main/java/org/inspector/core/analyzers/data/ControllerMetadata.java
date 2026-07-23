package org.inspector.core.analyzers.data;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.Expression;
import org.inspector.core.analyzers.endpoints.EndpointMetadata;
import org.inspector.core.analyzers.utils.ValidateAnnotationUtil;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ControllerMetadata implements MetadataAvaliable{
    private ClassOrInterfaceDeclaration clazz;
    private Path path;
    private List<EndpointMetadata> endpoints;

    public ControllerMetadata(ClassOrInterfaceDeclaration clazz, Path path) {
        this.clazz = clazz;
        this.path = path;
        this.endpoints = new ArrayList<>();
    }

    public void addEndpoint (EndpointMetadata endpointMetadata) {
        Optional<AnnotationExpr> annotationExpr = this.getClazz().getAnnotationByName("RequestMapping");
        Optional<Expression> expression = Optional.empty();
        if (annotationExpr.isPresent()) {
            expression = ValidateAnnotationUtil.getValueByAnnotation(
                    annotationExpr.get(),
                    new String[]{"value", "path"}
            );
        }

        // Adiciona Endpoint
        expression.ifPresent(value -> {
            endpointMetadata.setClassPath(value.toString());
            endpointMetadata.setOwner(this);
        });

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

    public Path getPath() {
        return path;
    }
}
