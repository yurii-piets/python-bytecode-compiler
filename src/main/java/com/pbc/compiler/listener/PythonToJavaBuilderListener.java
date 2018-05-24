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
    public void enterStmt(Python3Parser.StmtContext ctx) {
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
        }
        statementContextStack.push(statementContext);
    }

    @Override
    public void exitStmt(Python3Parser.StmtContext ctx) {
        builder.append(";\n");
    }

    @Override
    public void visitTerminal(TerminalNode node) {
        if (node.getSymbol().getText().equals("=")) {
            builder.append("=");
        }
    }

    @Override
    public void enterSimple_stmt(Python3Parser.Simple_stmtContext ctx) {
        super.enterSimple_stmt(ctx);
    }

    @Override
    public void enterSmall_stmt(Python3Parser.Small_stmtContext ctx) {
        super.enterSmall_stmt(ctx);
    }

    @Override
    public void enterExpr_stmt(Python3Parser.Expr_stmtContext ctx) {
        super.enterExpr_stmt(ctx);
    }

    @Override
    public void exitExpr_stmt(Python3Parser.Expr_stmtContext ctx) {
        switch (statementContextStack.pop()) {
            case FUNCTION_CALL:
                builder.append(")");
        }
    }
}
