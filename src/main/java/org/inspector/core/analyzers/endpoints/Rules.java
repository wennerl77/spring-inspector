package org.inspector.core.analyzers.endpoints;

public enum Rules {
    MAX_ENDPOINTS(20);

    private final int rule;

    Rules(int rule) {
        this.rule = rule;
    }

    public int getRule() {
        return rule;
    }
}
