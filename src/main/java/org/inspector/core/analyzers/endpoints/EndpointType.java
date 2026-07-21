package org.inspector.core.analyzers.endpoints;

public enum EndpointType {
    GENERIC("RequestMapping"),
    GET("GetMapping"),
    POST("PostMapping"),
    PATH("PathMapping"),
    PUT("PutMapping"),
    DELETE("DeleteMapping");

    private final String annotation;

    EndpointType(String annotation) {
        this.annotation = annotation;
    }

    public String getAnnotation() {
        return annotation;
    }
}
