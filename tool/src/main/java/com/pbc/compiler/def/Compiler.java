package com.pbc.compiler.def;

import java.io.File;
import java.io.IOException;

public interface Compiler {

    String compile(File file) throws IOException;

    String compile(String s);
}
