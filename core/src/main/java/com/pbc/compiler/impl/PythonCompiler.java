package com.pbc.compiler.impl;

import com.pbc.Python3Lexer;
import com.pbc.Python3Parser;
import com.pbc.compiler.ClassBuilder;
import com.pbc.compiler.def.Compiler;
import com.pbc.compiler.info.access.ClassAccessModifier;
import com.pbc.compiler.info.java.JavaVersion;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class PythonCompiler implements Compiler {

    @Override
    public String compile(File file) {

        ClassBuilder classBuilder = ClassBuilder.builder()
                .javaVersion(JavaVersion._8)
                .name(file.getName().replace(".", "$"))
                .superName("java/lang/Object")
                .accessModifiers(ClassAccessModifier.ACC_PUBLIC, ClassAccessModifier.ACC_SUPER);

        return classBuilder.build();
    }

    private void antlrDemo(File file) throws IOException {
        ANTLRInputStream inputStream = new ANTLRInputStream(new FileInputStream(file));
        Python3Lexer pythonLexer = new Python3Lexer(inputStream);
        CommonTokenStream commonTokenStream = new CommonTokenStream(pythonLexer);
        Python3Parser pythonParser = new Python3Parser(commonTokenStream);

        Python3Parser.File_inputContext fileContext = pythonParser.file_input();
    }
}
