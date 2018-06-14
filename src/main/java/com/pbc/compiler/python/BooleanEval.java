package com.pbc.compiler.python;

public class BooleanEval {

    public static boolean eval(String operator, Double arg1, Double arg2){
        switch (operator.trim()) {
            case "<":
                return arg1 < arg2;
            case ">":
                return arg1 > arg2;
            case "<=":
                return arg1 <= arg2;
            case ">=":
                return arg1 >= arg2;
            case "==":
                return arg1.equals(arg2);
            case "!=":
                return !arg1.equals(arg2);
        }
        throw new UnsupportedOperationException(operator);
    }

    public static boolean eval(String operator, Long arg1, Long arg2){
        switch (operator.trim()) {
            case "<":
                return arg1 < arg2;
            case ">":
                return arg1 > arg2;
            case "<=":
                return arg1 <= arg2;
            case ">=":
                return arg1 >= arg2;
            case "==":
                return arg1.equals(arg2);
            case "!=":
                return !arg1.equals(arg2);
        }
        throw new UnsupportedOperationException(operator);
    }

    public static boolean eval(String operator, Float arg1, Float arg2){
        switch (operator.trim()) {
            case "<":
                return arg1 < arg2;
            case ">":
                return arg1 > arg2;
            case "<=":
                return arg1 <= arg2;
            case ">=":
                return arg1 >= arg2;
            case "==":
                return arg1.equals(arg2);
            case "!=":
                return !arg1.equals(arg2);
        }
        throw new UnsupportedOperationException(operator);
    }

    public static boolean eval(String operator, Integer arg1, Integer arg2){
        switch (operator.trim()) {
            case "<":
                return arg1 < arg2;
            case ">":
                return arg1 > arg2;
            case "<=":
                return arg1 <= arg2;
            case ">=":
                return arg1 >= arg2;
            case "==":
                return arg1.equals(arg2);
            case "!=":
                return !arg1.equals(arg2);
        }
        throw new UnsupportedOperationException(operator);
    }
}
