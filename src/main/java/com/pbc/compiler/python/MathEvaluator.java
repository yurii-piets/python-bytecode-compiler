package com.pbc.compiler.python;

public class MathEvaluator {

    public static Number eval(String operator, Double var1, Double var2) {
        switch (operator) {
            case "+":
                return var1 + var2;
            case "-":
                return var1 - var2;
            case "*":
                return var1 * var2;
            case "/":
                return var1 / var2;
            case "%":
                return var1 % var2;
        }
        throw new UnsupportedOperationException(operator);
    }

    public static Number eval(String operator, Float var1, Float var2) {
        switch (operator) {
            case "+":
                return var1 + var2;
            case "-":
                return var1 - var2;
            case "*":
                return var1 * var2;
            case "/":
                return var1 / var2;
            case "%":
                return var1 % var2;
        }        throw new UnsupportedOperationException(operator);
    }

    public static Number eval(String operator, Long var1, Long var2) {
        switch (operator) {
            case "+":
                return var1 + var2;
            case "-":
                return var1 - var2;
            case "*":
                return var1 * var2;
            case "/":
                return var1 / var2;
            case "%":
                return var1 % var2;
        }        throw new UnsupportedOperationException(operator);
    }

    public static Number eval(String operator, Integer var1, Integer var2) {
        switch (operator) {
            case "+":
                return var1 + var2;
            case "-":
                return var1 - var2;
            case "*":
                return var1 * var2;
            case "/":
                return var1 / var2;
            case "%":
                return var1 % var2;
        }
        throw new UnsupportedOperationException(operator);
    }
}
