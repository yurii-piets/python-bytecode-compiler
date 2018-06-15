package com.pbc;

import com.pbc.compiler.def.Compiler;
import com.pbc.compiler.impl.PythonCompiler;
import com.pbc.reader.impl.ConsoleReader;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ApplicationManager {

    public final static String INLINE_COMPILE_COMMAND_PARAMETER = "-p";

    private List<String> args;

    private ConsoleReader reader;

    private Compiler compiler;

    ApplicationManager(List<String> args) {
        this.args = args;
        this.reader = new ConsoleReader(args);
        this.compiler = new PythonCompiler();
    }

    void run() throws IOException {
        if (args.contains(INLINE_COMPILE_COMMAND_PARAMETER)) {
            List<File> files = reader.readSingleFromCommandLine();
            for (File file : files) {
                compiler.compile(file);
            }
        }
    }
}
