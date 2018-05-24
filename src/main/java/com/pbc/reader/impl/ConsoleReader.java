package com.pbc.reader.impl;

import com.pbc.reader.def.Reader;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static com.pbc.ApplicationManager.INLINE_COMPILE_COMMAND_PARAMETER;

@RequiredArgsConstructor
public class ConsoleReader implements Reader {

    private final List<String> args;

    public List<File> readSingleFromCommandLine() {
        List<String> fileNames = readFileNames();
        return fileNames.stream()
                .map(File::new)
                .filter(File::exists)
                .collect(Collectors.toList());
    }

    private List<String> readFileNames() {
        int indexOf = args.indexOf(INLINE_COMPILE_COMMAND_PARAMETER);
        List<String> filesNames = new LinkedList<>();
        for (int i = indexOf + 1; i < args.size(); ++i) {
            filesNames.add(args.get(i));
        }
        return filesNames;
    }
}
