package com.pbc.compiler.listener;

public enum StatementContext {
    FUNCTION_CALL,
    VARIABLE_INIT;

    public static StatementContext defineStatementContext(String nodeText) {
        if (nodeText.matches(".*\\(.*\\).*")) {
            return FUNCTION_CALL;
        }
        if (nodeText.contains("=")) {
            return VARIABLE_INIT;
        }
        throw new IllegalArgumentException("Statement context cannot be defined for [" + nodeText + "].");
    }
}
