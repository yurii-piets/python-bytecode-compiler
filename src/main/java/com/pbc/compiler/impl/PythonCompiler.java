package com.pbc.compiler.impl;

import com.pbc.compiler.def.Compiler;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.stream.Collectors;

public class PythonCompiler implements Compiler {

    @Override
    public String compile(File file) throws IOException {
        return compile(Files.readAllLines(file.toPath()).stream().collect(Collectors.joining()));
    }

    @Override
    public String compile(String s) {
        throw new NotImplementedException();
    }
}
