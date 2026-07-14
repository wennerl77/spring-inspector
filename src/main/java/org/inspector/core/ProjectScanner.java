package org.inspector.core;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;

public class ProjectScanner {
    private List<Path> list;
    Set<String> blackList = Set.of("target/");

    public List<Path> scanProject (Path root) throws IOException {
        if (!Files.isDirectory(root)) {
            System.err.println("Path is not a directory");
            System.exit(1);
        }
        if (list != null) return list;
        try (var stream = Files.walk(root)) {
            list = stream
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".java"))
                    .filter(path -> blackList.stream().noneMatch(path.toString()::contains))
                    .toList();
            return list;
        } catch (IOException ex) {
            System.err.println("Erro ao analisar repositório");
        }

        return null;
    }
}
