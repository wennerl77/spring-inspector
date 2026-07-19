    package org.inspector.core.analyzers.controllerAnalyzer.analysis;

    import org.inspector.core.analyzers.Analyzer;
    import org.inspector.core.analyzers.CodebaseAnalyzer;
    import org.inspector.core.analyzers.controllerAnalyzer.data.ControllerMetadata;
    import org.inspector.core.analyzers.controllerAnalyzer.data.EndpointMetadata;
    import org.inspector.core.issues.Issue;
    import org.inspector.core.issues.BeanType;
    import org.inspector.core.issues.IssueType;
    import org.inspector.core.issues.Severity;

    import java.util.*;


    /**
     * TODO: Terminar desenvolvimento da funcionalidade
     */
    public class DuplicateEndpoint implements Analyzer {

        private final Map<String, ControllerMetadata> controllers = CodebaseAnalyzer.getProjectIndex().getControllers();

        @Override
        public List<Issue> analyze() {
            List<Issue> issues = new ArrayList<>();
            final HashMap<String, List<EndpointMetadata>> endpoints = new HashMap<>();

            controllers.forEach((name, controllerMetadata) -> {
                controllerMetadata.getEndpoints().forEach(endpoint -> {
                    if (endpoint.getHttpMethod() == null) {
                        System.out.println(endpoint);
                        return;
                    }
                    String key = endpoint.getHttpMethod().name() + endpoint.getFullPath();
                    endpoints.computeIfAbsent(key, k -> new ArrayList<>());
                    endpoints.get(key).add(endpoint);
                });
            });

            endpoints.forEach((path, list) -> {
                if (list.size() > 1) {
                    StringBuilder description = new StringBuilder();
                    list.forEach(endpointMetadata -> description.append("\n- ".concat(endpointMetadata.getBasicMethodInfo())));
                    issues.add(new Issue(
                            Severity.ERROR,
                            IssueType.DUPLICATE_ENDPOINT,
                            BeanType.CONTROLLER,
                            list.getFirst().getHttpMethod().name() + " " + list.getFirst().getFullPath(),
                            "Found in:\n" + description,
                            null,
                            null,
                            null
                    ));
                }
            });

            if (issues.isEmpty()) {
                issues.add(new Issue(
                        Severity.INFO,
                        null,
                        BeanType.CONTROLLER,
                        "Endpoints repetidos",
                        "Nenhum endpoint repetido",
                        null,
                        null,
                        null
                ));
            }
            return issues;
        }
    }