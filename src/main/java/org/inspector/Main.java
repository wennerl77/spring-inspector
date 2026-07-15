package org.inspector;

import org.inspector.core.analyzers.CodebaseAnalyzer;
import org.inspector.core.reports.Report;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.Option;

import java.io.IOException;
import java.nio.file.Path;

@Command(name = "spring-inspector", mixinStandardHelpOptions = true, version = "0.1-BETA",
        description = "Spring codebase analyzer")
public class Main implements Runnable {

    @Parameters(index = "0", description = "The path the inspector will examine")
    String path;

    @Option(names = {"-c", "--count"})
    boolean count;

    private final Report report = new Report();

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

        if (count) report.sendReportCount();
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new Main()).execute(args);
        System.exit(exitCode);
    }
}
