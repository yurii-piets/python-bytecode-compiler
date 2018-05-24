package com.pbc.compiler.listener;

public enum StatementContext {
    FUNCTION_CALL,
    VARIABLE_INIT,
    PRIMITIVE_DECLARATION,
    CHARACTER_DECLARATION,
    UNKNOWN;

    public static StatementContext defineStatementContext(String nodeText) {
        if (nodeText.matches(".*\\(.*\\).*")) {
            return FUNCTION_CALL;
        }
        if (nodeText.contains("=")) {
            return VARIABLE_INIT;
        }
        if (nodeText.matches("([0-9])|([0-9]*\\.[0-9]*)|(\".*\")")) {
            return PRIMITIVE_DECLARATION;
        }
        if (nodeText.matches("'.*'")) {
            return CHARACTER_DECLARATION;
        }
        return UNKNOWN;
//        throw new IllegalArgumentException("Statement context cannot be defined for [" + nodeText + "].");
    }
}
