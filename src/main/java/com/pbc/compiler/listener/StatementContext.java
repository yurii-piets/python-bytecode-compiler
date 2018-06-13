package com.pbc.compiler.listener;

public enum StatementContext {
    FUNCTION_CALL,
    PRIMITIVE_DECLARATION,
    CHARACTER_DECLARATION,
    VARIABLE_DECLARATION,
    UNKNOWN;

    public static StatementContext defineStatementContext(String nodeText) {
        if (nodeText.matches(".*\\(.*\\).*")) {
            return FUNCTION_CALL;
        }
        if (nodeText.matches("([0-9])|([0-9]*\\.[0-9]*)|(\".*\")")) {
            return PRIMITIVE_DECLARATION;
        }
        if (nodeText.matches("'.*'")) {
            return CHARACTER_DECLARATION;
        }
        if(nodeText.matches("[a-zA-Z]*")){
            return VARIABLE_DECLARATION;
        }
        return UNKNOWN;
    }
}
