package org.inspector.core.analyzers;

import com.github.javaparser.ParserConfiguration;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.RecordDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import org.inspector.core.ProjectScanner;
import org.inspector.core.analyzers.data.ControllerMetadata;
import org.inspector.core.analyzers.data.MethodMetaData;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class CodebaseAnalyzer {

    private final List<Path> javaClasses;

    private static final ProjectIndex projectIndex = new ProjectIndex();

    public CodebaseAnalyzer(Path path) throws IOException {
        this.javaClasses = new ProjectScanner().scanProject(path);
    }

    public void initAnalyzer() {
        config();
        javaClasses.forEach(path -> {
            try {
                CompilationUnit compilationUnit = StaticJavaParser.parse(path);
                compilationUnit.findAll(TypeDeclaration.class)
                        .forEach(td -> {
                            if (td instanceof ClassOrInterfaceDeclaration c) {
                                projectIndex.mapClass(c.getNameAsString(), path, c);

                                c.getAnnotations().forEach(annotationExpr -> {
                                    String formatedAnnotation = annotationExpr.getName().toString();
                                    projectIndex.addClass(formatedAnnotation, c);
                                });

                                c.getMethods().forEach(methodDeclaration -> {
                                    methodDeclaration.getAnnotations().forEach(annotationExpr -> {
                                        String formatedAnnotation = annotationExpr.getName().toString();
                                        projectIndex.addMethod(formatedAnnotation, new MethodMetaData(c, methodDeclaration));
                                    });
                                });
                            }
                            if (td instanceof RecordDeclaration r) {
                                projectIndex.mapRecord(r.getNameAsString(), r);
                            }
                        });

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void config() {
        StaticJavaParser.getParserConfiguration().setLanguageLevel(ParserConfiguration.LanguageLevel.JAVA_25);
    }

    public static ProjectIndex getProjectIndex() {
        return projectIndex;
    }

}
