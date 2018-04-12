package com.pbc.writer.impl;

import com.pbc.writer.def.Writer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class FileWriter implements Writer {

    @Override
    public void write(File inputFile, List<String> output) throws IOException {
        String outputFileName = createOutputFileName(inputFile);
        File outputFile = new File(outputFileName);
        if (!outputFile.exists()) {
            if (!outputFile.createNewFile()) {
                throw new IOException();
            }
        }

        if (!outputFile.setWritable(true)) {
            throw new IOException();
        }

        Files.write(outputFile.toPath(), output);
    }

    private String createOutputFileName(File inputFile) {
        String relativePath = inputFile.getPath();
        return relativePath.replace(".", "$")
                .concat(".class");
    }
}
