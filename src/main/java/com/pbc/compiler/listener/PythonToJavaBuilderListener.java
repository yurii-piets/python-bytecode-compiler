package com.pbc.compiler.listener;

import com.pbc.compiler.gen.Python3BaseListener;
import com.pbc.compiler.gen.Python3Parser;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class PythonToJavaBuilderListener extends Python3BaseListener {

    private Map<String, String> variablesTypes;

    private String varType;

    private Set<String> funNames;

    private StringBuilder builder;

    private int variableCounter = 0;

    private Stack<String> vatNamesStack = new Stack<>();


    public PythonToJavaBuilderListener(StringBuilder builder) {
        this.builder = builder;
        variablesTypes = new HashMap<>();
        //  variablesCounter = 0;
        funNames = new HashSet<>();
        funNames.add("print");
        funNames.add("range");
    }

    private String checkVariableValueType(String variableValue) {
        if (variableValue.contains("\"")) {
            varType = "_s";
            return "String";

        } else if (variableValue.contains(".")) {
            varType = "_d";
            return "double";
        } else {
            varType = "_i";
            return "int";
        }
    }


    @Override
    public void enterExpr_stmt(Python3Parser.Expr_stmtContext ctx) {
        if (!ctx.getText().contains("print")) {
            String variableType = checkVariableValueType(ctx.children.get(ctx.children.size() - 1).getText());
            String variableName = ctx.start.getText() + varType;
            if (!funNames.contains(ctx.start.getText())) {
                if (!variablesTypes.containsKey(variableName)) {
                    builder.append(variableType + " ");
                    variablesTypes.put(variableName, variableType);
                }
            }
        }
    }

    @Override
    public void exitExpr_stmt(Python3Parser.Expr_stmtContext ctx) {
        builder.append(";\n");
    }

    @Override
    public void visitTerminal(TerminalNode node) {
        String nodeText = node.getSymbol().getText();
        if (nodeText.equals("=") || nodeText.equals("else")) {
            builder.append(nodeText);
        }
        if (nodeText.equals("elif")) {
            builder.append("else if");
        }
    }

    @Override
    public void enterAtom_expr(Python3Parser.Atom_exprContext ctx) {
        if (ctx.start.getText().contains("print")) {
            String builderVarName = initializeClassVariable(StringBuilder.class);
            vatNamesStack.push(builderVarName);
        } else if (variablesTypes.containsKey(ctx.start.getText() + varType)) {
            builder.append(ctx.start.getText()).append(varType);
        } else {
            if (vatNamesStack.size() > 0) {
                builder.append(vatNamesStack.peek())
                        .append(".append(")
                        .append(ctx.start.getText())
                        .append(");")
                        .append("\n");
            } else {
                builder.append(ctx.start.getText());
            }
        }
    }

    private String initializeClassVariable(Class clazz) {
        String varName = "var" + getVariableCounter();
        builder.append(clazz.getCanonicalName()).append(" ").append(varName)
                .append(" = new ").append(clazz.getCanonicalName()).append("()")
                .append(";\n");
        return varName;
    }

    @Override
    public void exitAtom_expr(Python3Parser.Atom_exprContext ctx) {
        if (ctx.start.getText().contains("print")) {
            if (ctx.start.getText().equals("print")) {
                builder.append("System.out.print(");
            } else if (ctx.start.getText().equals("println")) {
                builder.append("System.out.println(");
            }
            builder.append(vatNamesStack.pop()).append(".toString()")
                    .append(")");
        }
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
    public void enterFor_stmt(Python3Parser.For_stmtContext ctx) {
        builder.append("for(");
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
    public void enterComp_op(Python3Parser.Comp_opContext ctx) {
        builder.append(ctx.start.getText());
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
        builder.append(ctx.start.getText());
    }

    private int getVariableCounter() {
        return variableCounter++;
    }
}
