package org.inspector;

import org.inspector.core.analyzers.CodebaseAnalyzer;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import java.io.IOException;
import java.nio.file.Path;

@Command(name = "spring-inspector", mixinStandardHelpOptions = true, version = "0.1-BETA",
        description = "Spring codebase analyzer")
public class Main implements Runnable {

    @Parameters(index = "0", description = "The path the inspector will examine")
    private String path;

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
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new Main()).execute(args);
        System.exit(exitCode);
    }
}
