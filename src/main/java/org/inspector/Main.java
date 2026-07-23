package org.inspector;

import org.inspector.core.analyzers.AnalysisResult;
import org.inspector.core.analyzers.CodebaseAnalyzer;
import org.inspector.core.analyzers.beans.CountAnaliser;
import org.inspector.core.analyzers.endpoints.DuplicateEndpoint;
import org.inspector.core.analyzers.endpoints.MissingRequestValidation;
import org.inspector.core.analyzers.endpoints.QualityController;
import org.inspector.core.report.Issue;
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

        if (count) {
            analysisResult.add(new AnalysisResult("Bean Counting", new CountAnaliser()));
            analysisResult.add(new AnalysisResult("Valid not set", new MissingRequestValidation()));
        }

        if (endpoint) {
            analysisResult.add(new AnalysisResult("Duplicate Endpoints", new DuplicateEndpoint()));
            analysisResult.add(new AnalysisResult("Quality Endpoints", new QualityController()));
        }

        analysisResult.forEach(AnalysisResult::print);
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new Main()).execute(args);
        System.exit(exitCode);
    }
}
