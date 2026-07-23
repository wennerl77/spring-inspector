package org.inspector.core.analyzers.endpoints;

import org.inspector.core.analyzers.Analyzer;
import org.inspector.core.analyzers.CodebaseAnalyzer;
import org.inspector.core.analyzers.data.ControllerMetadata;
import org.inspector.core.report.BeanType;
import org.inspector.core.report.Issue;
import org.inspector.core.report.IssueType;
import org.inspector.core.report.Severity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class QualityController implements Analyzer {

    @Override
    public List<Issue> analyze() {
        List<Issue> issues = new ArrayList<>();
        Map<String, ControllerMetadata> controllers = CodebaseAnalyzer.getProjectIndex().getControllers();

        // Verificação da quantidade de Endpoints em cada Controller
        controllers.values().forEach(controllerMetadata -> {
            int endpointsCount = controllerMetadata.getEndpoints().size();

            if (endpointsCount > Rules.MAX_ENDPOINTS.getRule()) {
                issues.add( new Issue(
                        Severity.WARNING,
                        IssueType.EXCEEDS_RECOMMENDED,
                        BeanType.CONTROLLER,
                        "Exceeds recommended endpoints",
                        "Controller exceeds recommended endpoint count, recommended is %d, but your Controller has %d"
                                .formatted(Rules.MAX_ENDPOINTS.getRule(), endpointsCount),
                        null,
                        controllerMetadata.getPath(),
                        "Split your Controller in smalls Controllers"
                ));
            }
        });

        if (issues.isEmpty()) {
            issues.add( new Issue(
                    Severity.INFO,
                    IssueType.EXCEEDS_RECOMMENDED,
                    BeanType.CONTROLLER,
                    "Exceeds recommended endpoints",
                    "Your Controllers are OK",
                    null,
                    null,
                    null
            ));
        }

        return issues;
    }
}
