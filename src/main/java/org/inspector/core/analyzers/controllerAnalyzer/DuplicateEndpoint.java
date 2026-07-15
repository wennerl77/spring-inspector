package org.inspector.core.analyzers.controllerAnalyzer;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import org.inspector.core.analyzers.Analyzer;
import org.inspector.core.analyzers.CodebaseAnalyzer;
import org.inspector.core.analyzers.MethodMetaData;
import org.inspector.core.analyzers.ProjectIndex;
import org.inspector.core.issues.Issue;
import org.inspector.core.issues.IssueType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class DuplicateEndpoint implements Analyzer {

    private final ProjectIndex projectIndex = CodebaseAnalyzer.getProjectIndex();

    @Override
    public List<Issue> analyze() {
        List<ClassOrInterfaceDeclaration> restControllerList = projectIndex.getAnnotationsOfClass(IssueType.CONTROLLER.getAnnotation());
        List<Issue> issues = new ArrayList<>();

        HashMap<RequestMapping, List<MethodMetaData>> methods;

        String endpointBase = "";

        restControllerList.forEach(controller -> {
            controller.getAnnotationByName("RequestMapping")
                    .ifPresent(annotationExpr -> {
//                        endpointBase = ValidateAnnotationUtil.getValueByAnnotation("RequestMapping");
                    });
        });

        return null;
    }
}

class RequestMapping {
    String method;
    String endpoint;

    public RequestMapping(String method, String endpoint) {
        this.method = method;
        this.endpoint = endpoint;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        RequestMapping that = (RequestMapping) o;
        return Objects.equals(method, that.method) && Objects.equals(endpoint, that.endpoint);
    }

    @Override
    public int hashCode() {
        return Objects.hash(method, endpoint);
    }
}
