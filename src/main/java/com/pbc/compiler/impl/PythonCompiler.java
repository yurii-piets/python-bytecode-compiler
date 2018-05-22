package com.pbc.compiler.impl;

import com.pbc.compiler.def.Compiler;
import com.pbc.compiler.gen.Python3Lexer;
import com.pbc.compiler.gen.Python3Parser;
import com.pbc.compiler.listener.PythonToJavaBuilderListener;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.stream.Collectors;

public class PythonCompiler implements Compiler {

    @Override
    public String compile(File file) throws IOException {
        String collect = Files.readAllLines(file.toPath()).stream().collect(Collectors.joining());
        return compile(collect, file.getName().replace(".", "$"));
    }

    private String compile(String s, String fileName) {
        ANTLRInputStream inputStream = new ANTLRInputStream(s);
        Python3Lexer pythonLexer = new Python3Lexer(inputStream);
        CommonTokenStream commonTokenStream = new CommonTokenStream(pythonLexer);
        Python3Parser pythonParser = new Python3Parser(commonTokenStream);
        StringBuilder builder = new StringBuilder();
        ParseTreeWalker.DEFAULT.walk(new PythonToJavaBuilderListener(builder), pythonParser.file_input());
        return wrapWithMain(builder.toString(), fileName);
    }

    private String wrapWithMain(String s, String className) {
        return "public class " + className + " {\n"
                + "\tpublic static void main(String... args){\n"
                + "\t\t" + s.replace(";\n", ";\n\t\t")
                + "}\n"
                + "}";
    }
}
