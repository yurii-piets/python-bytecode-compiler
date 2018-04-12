package com.pbc.writer.def;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface Writer {

    void write(File inputFile, List<String> output) throws IOException;
}
