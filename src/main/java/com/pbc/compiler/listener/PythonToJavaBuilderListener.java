package com.pbc.compiler.listener;

import com.pbc.compiler.gen.Python3BaseListener;
import com.pbc.compiler.gen.Python3Parser;
import com.pbc.compiler.python.PythonOutput;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.HashSet;
import java.util.Set;

import static com.pbc.compiler.listener.StatementContext.CHARACTER_DECLARATION;
import static com.pbc.compiler.listener.StatementContext.PRIMITIVE_DECLARATION;
import static com.pbc.compiler.listener.StatementContext.VARIABLE_DECLARATION;

@RequiredArgsConstructor
public class PythonToJavaBuilderListener extends Python3BaseListener {

    private final StringBuilder builder;

    private final FunctionMapper functionMapper = new FunctionMapper();

    private final Set<String> definedVariables = new HashSet<>();

    private StatementContext statementContext;

    @Override
    public void enterExpr_stmt(Python3Parser.Expr_stmtContext ctx) {
    }

    @Override
    public void enterAtom_expr(Python3Parser.Atom_exprContext ctx) {
        String nodeText = ctx.getText();
        String startText = ctx.start.getText();
        switch (startText) {
            case "print":
            case "println":
                builder.append(PythonOutput.class.getCanonicalName()).append(".").append(startText);
                String[] split = nodeText.split(",");
                if (split[split.length - 1].matches("end( )*=( )*\".*")) {
                    builder.append("WithEnd");
                } else if (split[split.length - 1].matches("sep( )*=( )*\".*")) {
                    builder.append("WithSeparator");
                }
                builder.append("(");
                break;
            case "min":
            case "max":
                builder.append(functionMapper.get(startText))
                        .append("(");
                break;
            default: {
                StatementContext statementContext = StatementContext.defineStatementContext(nodeText);
                if (this.statementContext == null && statementContext == StatementContext.VARIABLE_DECLARATION) {
                    String varName = startText;
                    if (!definedVariables.contains(varName)) {
                        builder.append("Object ");
                        definedVariables.add(varName);
                        this.statementContext = VARIABLE_DECLARATION;
                    }
                    builder.append(varName);
                } else if (this.statementContext == VARIABLE_DECLARATION) {
                    if (statementContext == PRIMITIVE_DECLARATION) {
                        builder.append(startText);
                    } else if (statementContext == CHARACTER_DECLARATION) {
                        builder.append(startText.replaceAll("'", "\""));
                    }
                    this.statementContext = null;
                } else {
                    builder.append(startText);
                }
            }
        }
    }

    @Override
    public void visitTerminal(TerminalNode node) {
        String nodeText = node.getSymbol().getText();
        switch (nodeText) {
            case "=":
            case "+":
            case "-":
            case "/":
            case "%":
                builder.append(nodeText);
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
    public void enterWhile_stmt(Python3Parser.While_stmtContext ctx) {
        builder.append("while");
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
        if (ctx.start.getText().contains("print")
                || ctx.start.getText().contains("min")
                || ctx.start.getText().contains("max")) {
            builder.append(")");
        }
    }

    @Override
    public void enterSuite(Python3Parser.SuiteContext ctx) {
        builder.append(" {\n");
    }

    @Override
    public void exitSuite(Python3Parser.SuiteContext ctx) {
        builder.append("} ");
        definedVariables.clear();
    }

    @Override
    public void enterAugassign(Python3Parser.AugassignContext ctx) {
        builder.append(ctx.getText());
    }
}
