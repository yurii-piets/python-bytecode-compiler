package com.pbc.compiler.listener;

import com.pbc.compiler.gen.Python3BaseListener;
import com.pbc.compiler.gen.Python3Parser;
import com.pbc.compiler.python.PythonOutput;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.tree.TerminalNode;

@RequiredArgsConstructor
public class PythonToJavaBuilderListener extends Python3BaseListener {

    private final StringBuilder builder;

    private final FunctionMapper functionMapper = new FunctionMapper();

    @Override
    public void enterExpr_stmt(Python3Parser.Expr_stmtContext ctx) {
    }

    @Override
    public void enterAtom_expr(Python3Parser.Atom_exprContext ctx) {
        String nodeText = ctx.getText();
        switch (ctx.start.getText()) {
            case "print":
            case "println":
                builder.append(PythonOutput.class.getCanonicalName()).append(".").append(ctx.start.getText());
                String[] split = nodeText.split(",");
                if (split[split.length - 1].matches("end( )*=( )*\".*")) {
                    builder.append("WithEnd");
                } else if (split[split.length - 1].matches("sep( )*=( )*\".*")) {
                    builder.append("WithSeparator");
                }
                builder.append("(");
                break;
            default:
                builder.append(nodeText);
        }
    }

    @Override
    public void visitTerminal(TerminalNode node) {
        String nodeText = node.getSymbol().getText();
        switch (nodeText) {
            case "=":
                builder.append("=");
                break;
            case "+":
                builder.append(" + ");
                break;
            case ",":
                builder.append(", ");
                break;
            case "else":
                builder.append("else");
                break;
            case "elif":
                builder.append("else if");
                break;
        }
    }

    @Override
    public void exitExpr_stmt(Python3Parser.Expr_stmtContext ctx) {
        builder.append(";\n");
    }

    @Override
    public void enterIf_stmt(Python3Parser.If_stmtContext ctx) {
        builder.append("if");
    }

    @Override
    public void enterComp_op(Python3Parser.Comp_opContext ctx) {
        builder.append(ctx.start.getText());
    }

    @Override
    public void enterComparison(Python3Parser.ComparisonContext ctx) {
        if (ctx.children.size() > 1) {
            builder.append("(");
        }
    }

    @Override
    public void exitComparison(Python3Parser.ComparisonContext ctx) {
        if (ctx.children.size() > 1) {
            builder.append(")");
        }
    }

    @Override
    public void exitAtom_expr(Python3Parser.Atom_exprContext ctx) {
        if (ctx.start.getText().contains("print")) {
            builder.append(")");
        }
    }

    @Override
    public void enterSuite(Python3Parser.SuiteContext ctx) {
        builder.append("{");
    }

    @Override
    public void exitSuite(Python3Parser.SuiteContext ctx) {
        builder.append("}");
    }

    @Override
    public void enterAugassign(Python3Parser.AugassignContext ctx) {
        builder.append(ctx.getText());
    }
}
