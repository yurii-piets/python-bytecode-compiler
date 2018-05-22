package com.pbc.compiler.listener;

import com.pbc.compiler.gen.Python3BaseListener;
import com.pbc.compiler.gen.Python3Parser;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PythonToJavaListener extends Python3BaseListener {

    private Map<String,String> variablesTypes;
    private String varType;
    private Set<String> funNames;


    public PythonToJavaListener() {
        variablesTypes = new HashMap<>();
      //  variablesCounter = 0;
        funNames = new HashSet<>();
        funNames.add("print");
        funNames.add("range");
    }

    private String checkVariableValueType(String variableValue) {
        if(variableValue.contains("\"")) {
            varType = "_s";
            return "String";

        }
        else if(variableValue.contains(".")) {
            varType = "_d";
            return "double";
        }
        else {
            varType = "_i";
            return "int";
        }
    }



    @Override public void enterExpr_stmt(Python3Parser.Expr_stmtContext ctx) {


        String variableType = checkVariableValueType(ctx.children.get(ctx.children.size()-1).getText());
        String variableName = ctx.start.getText() + varType;
        if(!funNames.contains(ctx.start.getText())){
            if(!variablesTypes.containsKey(variableName)){
                System.out.print(variableType+ " ");
                variablesTypes.put(variableName,variableType);
            }
//            else {
////            if(!variablesTypes.get(variableName).equals(variableType)) {
//                int counter = 1;
//                String tmp = variableName + counter;
//                while(variablesTypes.containsKey(tmp)){
//                    counter++;
//                    tmp = variableName + counter;
//                }
//                System.out.print(variableType + " ");
//                variablesTypes.put(tmp,variableType);
//                // variablesCounter = counter;
//                variablesCounter = counter;
//                //variablesCounter.put(variableName,counter);
////            }
//            }
        }
    }

    @Override public void exitExpr_stmt(Python3Parser.Expr_stmtContext ctx) {
        System.out.print(";\n");
    }

    @Override public void visitTerminal(TerminalNode node) {
        if(node.getSymbol().getText().equals("=") || node.getSymbol().getText().equals("else")) System.out.print(node.getSymbol().getText());
        if(node.getSymbol().getText().equals("elif")) System.out.print("else if");

    }

    @Override public void enterAtom_expr(Python3Parser.Atom_exprContext ctx) {
        if(ctx.start.getText().equals("print")) System.out.print("System.out.println(");
        else if(variablesTypes.containsKey(ctx.start.getText()+varType)) {
            System.out.print(ctx.start.getText()+varType);
            //variablesCounter = 0;
        }else {
            System.out.print(ctx.start.getText());
        }
    }

    @Override public void exitAtom_expr(Python3Parser.Atom_exprContext ctx) {
        if(ctx.start.getText().equals("print")) System.out.print(")");
    }


    @Override public void enterIf_stmt(Python3Parser.If_stmtContext ctx) {
        System.out.print("if");
    }

    @Override public void enterWhile_stmt(Python3Parser.While_stmtContext ctx) {
        System.out.print("while");
    }
    @Override public void enterFor_stmt(Python3Parser.For_stmtContext ctx) {
        System.out.print("for(");
    }

    @Override public void enterComparison(Python3Parser.ComparisonContext ctx) {

        if(ctx.children.size() > 1) System.out.print("(");
    }


    @Override public void exitComparison(Python3Parser.ComparisonContext ctx) {
        if(ctx.children.size() > 1) System.out.print(")");
    }

    @Override public void enterComp_op(Python3Parser.Comp_opContext ctx) {
        System.out.print(ctx.start.getText());
    }

    @Override public void enterSuite(Python3Parser.SuiteContext ctx) {
        System.out.println("{");
    }

    @Override public void exitSuite(Python3Parser.SuiteContext ctx) {
        System.out.println("}");
    }
    @Override public void enterAugassign(Python3Parser.AugassignContext ctx) {
        System.out.print(ctx.start.getText());
    }
}
