package com.pbc.compiler.def;

import java.io.File;
import java.io.IOException;

public interface Compiler {

    void compile(File file) throws IOException;
}
