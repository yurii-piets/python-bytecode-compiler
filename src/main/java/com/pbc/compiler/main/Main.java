package com.pbc.compiler.main;

import com.pbc.compiler.gen.Python3Lexer;
import com.pbc.compiler.gen.Python3Parser;
import com.pbc.compiler.listener.PythonToJavaListener;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.FileInputStream;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        ANTLRInputStream inputStream = new ANTLRInputStream(new FileInputStream("src\\main\\resources\\examples\\test.py"));
        Python3Lexer pythonLexer = new Python3Lexer(inputStream);
        CommonTokenStream commonTokenStream = new CommonTokenStream(pythonLexer);
        Python3Parser pythonParser = new Python3Parser(commonTokenStream);
       // pythonParser.addParseListener(new PythonToJavaListener());
        //System.out.println(pythonParser.file_input().children.size());
        //Trees.inspect(pythonParser.file_input(),pythonParser);
        ParseTreeWalker.DEFAULT.walk(new PythonToJavaListener(), pythonParser.file_input());
    }

}
