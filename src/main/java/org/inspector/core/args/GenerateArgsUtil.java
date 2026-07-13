package org.inspector.core.args;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class GenerateArgsUtil {

    public static Map<String, String> generateArgs(String[] args) {
        Map<String, String> map = new HashMap<>();
        String expectedArg = null;

        for (String arg : args) {
            if (arg.startsWith("-")) {
                arg = arg.replaceFirst("-", "");
                if (!ArgsService.isFlag(arg)) {
                    System.err.printf("Flag %s inválido%n", arg);
                    System.exit(1);
                }
                if (ArgsService.expectedArgs(arg)) expectedArg = arg;
                else map.put(arg, null);
            } else {
                if (expectedArg == null) {
                    System.err.println("Invalid args");
                    System.exit(1);
                }
                map.put(expectedArg, arg);
                expectedArg = null;
            }
        }

        validArgs(map);

        return map;
    }

    private static void validArgs(Map<String, String> map) {
        if (!map.containsKey(Args.PATH.getFlag())) {
            System.err.println("Path not found.\nAdd -p <directory>");
            System.exit(1);
        }
        Path path = Path.of(map.get(Args.PATH.getFlag()));
        if (!Files.exists(path)) {
            System.err.println("Path not found.\nAdd -p <directory>");
            System.exit(1);
        }
        if (!Files.isDirectory(path)) {
            System.err.println("Directory not found.\nAdd -p <directory>");
            System.exit(1);
        }
    }
}
