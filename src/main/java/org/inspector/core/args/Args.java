package org.inspector.core.args;

public enum Args {
    PATH("p"),
    VERBOSE("v"),
    VERSION("V");

    private String flag;

    Args(String flag) {
        this.flag = flag;
    }

    public String getFlag() {
        return flag;
    }
}
