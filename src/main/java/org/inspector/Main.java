package org.inspector;

import org.inspector.core.analyzers.AnalysisResult;
import org.inspector.core.analyzers.CodebaseAnalyzer;
import org.inspector.core.analyzers.GenericsAnalyzer.CountAnaliser;
import org.inspector.core.analyzers.controllerAnalyzer.analysis.DuplicateEndpoint;
import org.inspector.core.issues.Issue;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.Option;

import java.io.IOException;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Command(name = "spring-inspector", mixinStandardHelpOptions = true, version = "0.1-BETA",
        description = "Spring codebase analyzer")
public class Main implements Runnable {

    @Parameters(index = "0", description = "The path the inspector will examine")
    String path;

    @Option(names = {"-c", "--count"})
    boolean count;

    @Option(names = {"-e", "--endpoint"})
    boolean endpoint;

    private static final List<AnalysisResult> analysisResult = new ArrayList<>();

    @Override
    public void run() {

        System.out.println("""


                        .-===================-.       \s
                       .=======================.      \s
                      :=========================:     \s
                     .============: :============:    \s
                    :========::==-.  ===.:========:   \s
                  .:=======. ..==-.  ==-.. .=======:. \s
                  :=======. .====-.  ====-  .=======: \s
                .-=======. .=====-.  ======. .=======-.
                :========  :=====-.  ======:  ========-
                ========-  :======.  ======:  =========
                -========  .===============. .========-
                .-========. .=============. .========-.
                  :=======-  .:=========:.  ========: \s
                  .:========:  ....:....  -========:. \s
                    :==========:.     .-==========:   \s
                     :===========================:    \s
                      :=========================:     \s
                       .=======================.      \s
                        .-===================-.       \s
                
                    Analisando seu projeto Spring ...
                """);

        try {
            new CodebaseAnalyzer(Path.of(path)).initAnalyzer();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        Instant instantNow = Instant.now();
        if (count) analysisResult.add(new AnalysisResult("Bean Counting", (new CountAnaliser().analyze()), Duration.between(instantNow, Instant.now())));
        instantNow = Instant.now();
        if (endpoint) analysisResult.add(new AnalysisResult("Duplicate Endpoints", (new DuplicateEndpoint().analyze()), Duration.between(instantNow, Instant.now())));

        analysisResult.forEach(analysis -> {
            printLine("=", 50);
            System.out.println("\tAnalysis: " + analysis.name());
            printLine("=", 50);
            sendReport(analysis.issues());
            printLine("=", 50);
            System.out.println("\tDuration: " + analysis.duration().toMillis() + " ms");
            printLine("=", 50);

            System.out.println();
        });
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new Main()).execute(args);
        System.exit(exitCode);
    }

    static void sendReport(List<Issue> issues) {
        issues.forEach(System.out::println);
    }
    static void printLine(String caracter, int repeat) {
        System.out.println(caracter.repeat(repeat));
    }
}
