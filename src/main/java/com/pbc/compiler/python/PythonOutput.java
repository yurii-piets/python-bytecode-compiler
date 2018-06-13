package com.pbc.compiler.python;

public class PythonOutput {

    public static void print(PythonObject... args) {
        for (PythonObject arg : args) {
            System.out.print(arg.getObject());
        }
    }

    public static void println(PythonObject... args) {
        print(args);
        System.out.println();
    }

    public static void printWithSeparator(PythonObject sep, PythonObject... args) {
        for (PythonObject arg : args) {
            System.out.print(arg.getObject());
            System.out.print(sep.getObject());
        }
    }

    public static void printlnWithSeparator(PythonObject sep, PythonObject... args) {
        printWithSeparator(sep, args);
        System.out.println();
    }

    public static void printWithEnd(PythonObject end, PythonObject... args) {
        print(args);
        System.out.print(end);
    }

    public static void printlnWithEnd(PythonObject end, PythonObject... args) {
        printWithEnd(end, args);
        System.out.println();
    }
}
