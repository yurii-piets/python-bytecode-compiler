package com.pbc.compiler.listener;

import java.lang.reflect.Method;
import java.util.HashMap;

public class FunctionMapper extends HashMap<String, String> {

    public FunctionMapper() {
        put("print", "System.out.print");
        put("println", "System.out.println");

        for (Method method : Math.class.getMethods()) {
            put(method.getName(), "Math." + method.getName().replace("(", "").replace(")", ""));
        }
    }
}
