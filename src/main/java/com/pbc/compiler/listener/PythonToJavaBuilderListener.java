package com.pbc.compiler.listener;

import com.pbc.compiler.gen.Python3BaseListener;
import com.pbc.compiler.gen.Python3Parser;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.Stack;

@RequiredArgsConstructor
public class PythonToJavaBuilderListener extends Python3BaseListener {

    private final StringBuilder builder;

    private final FunctionMapper functionMapper = new FunctionMapper();

    private final Stack<StatementContext> statementContextStack = new Stack<>();

    @Override
    public void enterExpr_stmt(Python3Parser.Expr_stmtContext ctx) {

    }

    @Override
    public void enterAtom_expr(Python3Parser.Atom_exprContext ctx) {
        String nodeText = ctx.getText();
        StatementContext statementContext = StatementContext.defineStatementContext(nodeText);
        switch (statementContext) {
            case FUNCTION_CALL:
                builder.append(functionMapper.get(ctx.start.getText()))
                        .append("(");
                break;
            case VARIABLE_INIT:
                builder.append("Object ").append(ctx.start.getText());
                break;
            case PRIMITIVE_DECLARATION:
                builder.append(ctx.start.getText());
                break;
            case CHARACTER_DECLARATION:
                builder.append(ctx.start.getText().replaceAll("'", "\""));
                break;
        }
        if (statementContext != StatementContext.UNKNOWN) {
            statementContextStack.push(statementContext);
        }
    }

    @Override
    public void visitTerminal(TerminalNode node) {
        String nodeText = node.getSymbol().getText();
        if (nodeText.equals("=")) {
            builder.append("=");
        } else if (nodeText.equals(",") || nodeText.equals("+")) {
            if (statementContextStack.size() > 0) {
                switch (statementContextStack.pop()) {
                    case CHARACTER_DECLARATION:
                    case PRIMITIVE_DECLARATION:
                        builder.append(" + ");
                }
            }
        }
    }

    @Override
    public void exitExpr_stmt(Python3Parser.Expr_stmtContext ctx) {
        if (statementContextStack.size() > 0) {
            while (statementContextStack.peek() == StatementContext.CHARACTER_DECLARATION
                    || statementContextStack.peek() == StatementContext.PRIMITIVE_DECLARATION) {
                statementContextStack.pop();
            }
            switch (statementContextStack.pop()) {
                case FUNCTION_CALL:
                    builder.append(")");
            }
        }
        builder.append(";\n");
    }
}
