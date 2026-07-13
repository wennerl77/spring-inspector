package org.inspector.core.args;

import java.util.HashMap;
import java.util.Map;

public class ArgsService {
    private static final Map<String, Runnable> flags = new HashMap<>();

    static {
        flags.put("v", null);
        flags.put("V", null);
        flags.put("p", () -> {});
    }

    public static boolean isFlag(String flag) {
        return flags.containsKey(flag);
    }

    public static boolean expectedArgs(String flag) {
        if (isFlag(flag)) return flags.get(flag) != null;
        return false;
    }
}
