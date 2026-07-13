package org.inspector;

import org.inspector.core.analyzers.CodebaseAnalyzer;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            new CodebaseAnalyzer(args).initAnalyzer();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
