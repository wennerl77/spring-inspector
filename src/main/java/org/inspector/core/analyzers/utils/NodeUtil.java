package org.inspector.core.analyzers.utils;

import com.github.javaparser.ast.Node;

public class NodeUtil {
    public static Integer getLine (Node node) {
        return node.getRange().map(range -> range.begin.line)
                .orElse(-1);
    }
}
