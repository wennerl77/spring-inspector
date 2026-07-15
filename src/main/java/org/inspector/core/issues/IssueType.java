package org.inspector.core.issues;

public enum IssueType {
    CONTROLLER("Controller", "RestController"),
    SERVICE("Service", "Service"),
    REPOSITORY("Repository", "Repository"),
    MAPPER("Mapper", "Mapper"),
    SECURITY("Security", "EnableWebSecurity"),
    ENTITY("Entity", "Entity"),
    COMPONENT("Component", "Component");

    private final String name;
    private final String annotation;

    IssueType(String name, String annotation) {
        this.name = name;
        this.annotation = annotation;
    }

    public String getName() {
        return name;
    }

    public String getAnnotation() {
        return annotation;
    }
}