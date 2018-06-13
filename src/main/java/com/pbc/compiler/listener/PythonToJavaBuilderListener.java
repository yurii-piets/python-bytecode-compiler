package com.pbc.compiler.listener;

import com.pbc.compiler.gen.Python3BaseListener;
import com.pbc.compiler.gen.Python3Parser;
import com.pbc.compiler.python.PythonObject;
import com.pbc.compiler.python.PythonOutput;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import static com.pbc.compiler.listener.StatementContext.CHARACTER_DECLARATION;
import static com.pbc.compiler.listener.StatementContext.FUNCTION_CALL;
import static com.pbc.compiler.listener.StatementContext.PRIMITIVE_DECLARATION;
import static com.pbc.compiler.listener.StatementContext.VARIABLE_DECLARATION;

@RequiredArgsConstructor
public class PythonToJavaBuilderListener extends Python3BaseListener {

    private final StringBuilder builder;

    private final FunctionMapper functionMapper = new FunctionMapper();

    private final Set<String> definedVariables = new HashSet<>();

    private StatementContext statementContext;

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
            case "input":
                builder.append("new ").append(PythonObject.class.getCanonicalName()).append("(").append("new ").append(Scanner.class.getCanonicalName()).append("(System.in).nextLine()").append(")");
                break;
            default: {
                StatementContext statementContext = StatementContext.defineStatementContext(nodeText);
                if (this.statementContext == null && statementContext == StatementContext.VARIABLE_DECLARATION) {
                    if (!definedVariables.contains(startText)) {
                        builder.append(PythonObject.class.getCanonicalName()).append(" ");
                        definedVariables.add(startText);
                        this.statementContext = VARIABLE_DECLARATION;
                    }
                    builder.append(startText);
                } else if (this.statementContext == VARIABLE_DECLARATION) {
                    if (statementContext == CHARACTER_DECLARATION) {
                        String str = startText.replaceAll("'", "\"");
                        builder.append("new ").append(PythonObject.class.getCanonicalName()).append("(").append(str).append(")");
                    } else if (statementContext == PRIMITIVE_DECLARATION || definedVariables.contains(startText)) {
                        builder.append("new ").append(PythonObject.class.getCanonicalName()).append("(").append(startText).append(")");
                    }
                    this.statementContext = null;
                } else {
                    builder.append("new ").append(PythonObject.class.getCanonicalName()).append("(").append(startText).append(")");
                }
            }
        }
        if(statementContext == FUNCTION_CALL) {
            builder.append(")");
            statementContext = null;
        }
    }

    @Override
    public void visitTerminal(TerminalNode node) {
        String nodeText = node.getSymbol().getText();
        switch (nodeText) {
            case "=":
                builder.append(" ").append(nodeText).append(" ");
                break;
            case "+":
            case "-":
            case "*":
            case "/":
            case "%":
                builder.append(".invokeOperator(").append("\"").append(nodeText).append("\"").append(",");
                this.statementContext = FUNCTION_CALL;
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
