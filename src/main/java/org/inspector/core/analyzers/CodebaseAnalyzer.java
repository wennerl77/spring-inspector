package org.inspector.core.analyzers;

import com.github.javaparser.ParserConfiguration;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import org.inspector.core.ProjectScanner;
import org.inspector.core.args.Args;
import org.inspector.core.args.GenerateArgsUtil;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class CodebaseAnalyzer {

    private final Map<String, String> args; // Ignorado por enquanto
    private final List<Path> javaClasses;
    private final ProjectScanner projectScanner;

    private final Set<AnnotationExpr> classAnnotations = new HashSet<>();
    private final Set<AnnotationExpr> methodAnnotations = new HashSet<>();

    public CodebaseAnalyzer(String[] systemArgs) throws IOException {
        this.args = GenerateArgsUtil.generateArgs(systemArgs);
        this.projectScanner = new ProjectScanner();
        this.javaClasses = projectScanner.scanProject(Path.of(args.get(Args.PATH.getFlag())));
    }

    public void initAnalyzer() {
        config();
        javaClasses.forEach(path -> {
            try {
                System.out.println(path);
                CompilationUnit compilationUnit = StaticJavaParser.parse(path);
                compilationUnit.findAll(ClassOrInterfaceDeclaration.class)
                        .forEach(clazz -> {
                            classAnnotations.addAll(clazz.getAnnotations());
                            clazz.getMethods()
                                    .forEach(methodDeclaration -> methodAnnotations
                                            .addAll(methodDeclaration.getAnnotations()));
                        });
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void config() {
        StaticJavaParser.getParserConfiguration().setLanguageLevel(ParserConfiguration.LanguageLevel.JAVA_25);
    }
}
