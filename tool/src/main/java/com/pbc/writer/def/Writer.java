package com.pbc.writer.def;

import java.io.File;
import java.io.IOException;

public interface Writer {

    void write(File inputFile, String output) throws IOException;
}
