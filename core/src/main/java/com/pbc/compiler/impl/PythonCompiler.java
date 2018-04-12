package com.pbc.compiler.impl;

import com.pbc.Python3Lexer;
import com.pbc.Python3Parser;
import com.pbc.compiler.def.Compiler;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PythonCompiler implements Compiler {

    @Override
    public List<String> compile(File file) {
        try {
            antlrDemo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<String> output = new ArrayList<>();
        output.add("Not implemented");
        return output;
    }

    private void antlrDemo(File file) throws IOException {
        ANTLRInputStream inputStream = new ANTLRInputStream(new FileInputStream(file));
        Python3Lexer pythonLexer = new Python3Lexer(inputStream);
        CommonTokenStream commonTokenStream = new CommonTokenStream(pythonLexer);
        Python3Parser pythonParser = new Python3Parser(commonTokenStream);

        Python3Parser.File_inputContext fileContext = pythonParser.file_input();
    }
}
