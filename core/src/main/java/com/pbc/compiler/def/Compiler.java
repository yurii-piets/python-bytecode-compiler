package com.pbc.compiler.def;

import java.io.File;
import java.util.List;

public interface Compiler {

    List<String> compile(File file);
}
