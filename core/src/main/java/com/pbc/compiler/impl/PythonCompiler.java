package com.pbc.compiler.impl;

import com.pbc.compiler.def.Compiler;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PythonCompiler implements Compiler {

    @Override
    public List<String> compile(File file) {
        List<String> output = new ArrayList<>();
        output.add("Not implemented");
        return output;
    }
}
