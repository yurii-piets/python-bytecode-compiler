package com.pbc.compiler.python;

public class PythonOutput {

    public static void print(String... args) {
        for (String arg : args) {
            System.out.print(arg);
        }
    }

    public static void println(String... args) {
        print(args);
        System.out.println();
    }

    public static void printWithSeparator(String sep, String... args) {
        for (String arg : args) {
            System.out.print(arg + sep);
        }
    }

    public static void printlnWithSeparator(String sep, String... args) {
        printWithSeparator(sep, args);
        System.out.println();
    }

    public static void printWithEnd(String end, String... args) {
        print(args);
        System.out.print(end);
    }

    public static void printlnWithEnd(String end, String... args) {
        printWithEnd(end, args);
        System.out.println();
    }
}
