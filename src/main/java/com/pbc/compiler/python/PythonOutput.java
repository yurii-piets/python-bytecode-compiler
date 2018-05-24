package com.pbc.compiler.python;

public class PythonOutput {

    public static void print(Object... args) {
        for (Object arg : args) {
            System.out.print(arg);
        }
    }

    public static void println(Object... args) {
        print(args);
        System.out.println();
    }

    public static void printWithSeparator(Object sep, Object... args) {
        for (Object arg : args) {
            System.out.print(arg);
            System.out.print(sep);
        }
    }

    public static void printlnWithSeparator(Object sep, Object... args) {
        printWithSeparator(sep, args);
        System.out.println();
    }

    public static void printWithEnd(Object end, Object... args) {
        print(args);
        System.out.print(end);
    }

    public static void printlnWithEnd(Object end, Object... args) {
        printWithEnd(end, args);
        System.out.println();
    }
}
