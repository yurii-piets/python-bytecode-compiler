package com.pbc.compiler.impl;

import com.pbc.compiler.def.Compiler;
import com.pbc.compiler.gen.Python3Lexer;
import com.pbc.compiler.gen.Python3Parser;
import com.pbc.compiler.listener.PythonToJavaBuilderListener;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.Collectors;

public class PythonCompiler implements Compiler {

    @Override
    public void compile(File file) throws IOException {
        String collect = Files.readAllLines(file.toPath()).stream().collect(Collectors.joining());
        compile(collect, file.getName().replace(".", "$"));
    }

    private void compile(String s, String fileName) {
        String javaCode = compileToJava(s, fileName);
        compileToByteCode(javaCode, fileName);
    }

    private String compileToJava(String s, String fileName) {
        ANTLRInputStream inputStream = new ANTLRInputStream(s);
        Python3Lexer pythonLexer = new Python3Lexer(inputStream);
        CommonTokenStream commonTokenStream = new CommonTokenStream(pythonLexer);
        Python3Parser pythonParser = new Python3Parser(commonTokenStream);
        StringBuilder builder = new StringBuilder();
        ParseTreeWalker.DEFAULT.walk(new PythonToJavaBuilderListener(builder), pythonParser.file_input());
        return wrapWithMain(builder.toString(), fileName);
    }

    private void compileToByteCode(String javaCode, String fileName) {
        JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
        try {
            File file = new File(fileName + ".java");
            if (!file.exists()) {
                file.createNewFile();
                if (!file.canWrite()) {
                    file.setWritable(true);
                }
            }
            Path sourceFile = file.toPath();
            Files.write(sourceFile, javaCode.getBytes());
            javaCompiler.run(null, null, null, sourceFile.toFile().getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String wrapWithMain(String s, String className) {
        return "public class " + className + " {\n"
                + "\tpublic static void main(String[] args){\n"
                + "\t\t" + Arrays.stream(s.split(";\n")).collect(Collectors.joining( ";\n\t\t"))
                + ";\n\t}\n"
                + "}";
    }
}
