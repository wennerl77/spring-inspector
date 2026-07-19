package org.inspector.core.issues;

public enum BeanType {
    CONTROLLER("Controller", new String[] {"RestController"}),
    SERVICE("Service", new String[] {"Service"}),
    REPOSITORY("Repository", new String[] {"Repository"}),
    MAPPER("Mapper", new String[] {"Mapper"}),
    SECURITY("Security", new String[] {"EnableWebSecurity"}),
    ENTITY("Entity", new String[] {"Entity"}),
    COMPONENT("Component", new String[] {"Component"});

    private final String name;
    private final String[] annotations;

    BeanType(String name, String[] annotation) {
        this.name = name;
        this.annotations = annotation;
    }

    public String getName() {
        return name;
    }

    public String[] getAnnotations() {
        return annotations;
    }

    public String getAnnotationsConcat() {
        return String.join(", ", annotations);
    }
}