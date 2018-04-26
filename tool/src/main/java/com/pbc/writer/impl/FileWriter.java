package com.pbc.writer.impl;

import com.pbc.writer.def.Writer;

import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileWriter implements Writer {

    @Override
    public void write(File inputFile, String output) throws IOException {
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

        Files.write(outputFile.toPath(), hexStringToByteArray(output));
    }

    private String createOutputFileName(File inputFile) {
        String relativePath = inputFile.getPath();
        return relativePath.replace(".", "$")
                .concat(".class");
    }

    public static byte[] hexStringToByteArray(String s) {
        return DatatypeConverter.parseHexBinary(s);
    }
}
