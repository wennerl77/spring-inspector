package org.inspector.core.analyzers.endpoints;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import org.inspector.core.analyzers.Analyzer;
import org.inspector.core.analyzers.CodebaseAnalyzer;
import org.inspector.core.analyzers.ProjectIndex;
import org.inspector.core.analyzers.data.ControllerMetadata;
import org.inspector.core.analyzers.utils.NodeUtil;
import org.inspector.core.report.BeanType;
import org.inspector.core.report.Issue;
import org.inspector.core.report.IssueType;
import org.inspector.core.report.Severity;
import org.inspector.utils.color.AnsiColor;
import org.inspector.utils.color.PrintColorUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MissingRequestValidation implements Analyzer {
    @Override
    public List<Issue> analyze() {
        List<Issue> issues = new ArrayList<>();

        ProjectIndex projectIndex = CodebaseAnalyzer.getProjectIndex();
        Map<String, ControllerMetadata> controllers = projectIndex.getControllers();

        controllers.values().forEach(controllerMetadata -> {
            controllerMetadata.getEndpoints().forEach(endpointMetadata -> {
                MethodDeclaration method = endpointMetadata.getDeclaration();
                Integer line = NodeUtil.getLine(method);
                for (Parameter parameter : method.getParameters()) {
                    boolean isRequestBody = false, isValid = false, isJavaCLass = false;

                    if (parameter.getAnnotationByName("RequestBody").isPresent()) isRequestBody = true;

                    if (parameter.getAnnotationByName("Valid").isPresent() ||
                        parameter.getAnnotationByName("Validated").isPresent()) isValid = true;

                    if (!projectIndex.containsClass(parameter.getType().asString()) && !projectIndex.containsRecord(parameter.getType().asString())) {
                        isJavaCLass = true;
                    }

                    if (isRequestBody && !isValid && !isJavaCLass) {
                        issues.add(new Issue(Severity.WARNING,
                                IssueType.MISSING_REQUEST_VALIDATION,
                                BeanType.CONTROLLER,
                                "Missing Request Validation",
                                "Method parameter is not being validated.",
                                line,
                                controllerMetadata.getPath(),
                                "Add @Valid before the parameter " + PrintColorUtil.colorize(parameter.getNameAsString(), AnsiColor.RED)));
                    }
                }
            });
        });

        if (issues.isEmpty()) {
            issues.add(new Issue(Severity.INFO,
                    IssueType.MISSING_REQUEST_VALIDATION,
                    BeanType.CONTROLLER,
                    "Missing Request Validation",
                    "Method parameters it's OK",
                    null,
                    null,
                    null));
        }

        return issues;
    }
}
