package com.pbc.compiler.python;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@SuppressWarnings("unused")
public class PythonObject {

    private Object object;

    public PythonObject(Object object) {
        this.object = object;
    }

    public PythonObject(Void v) {
        this.object = v;
    }

    public PythonObject(PythonObject pythonObject) {
        this.object = pythonObject;
    }

    public PythonObject invokeMethod(String methodName, PythonObject... pythonObjects) {
        try {
            Class<?>[] classes = new Class[pythonObjects.length];
            for (int i = 0; i < pythonObjects.length; ++i) {
                classes[i] = pythonObjects.getClass();
            }
            Method declaredMethod = object.getClass().getDeclaredMethod(methodName, classes);
            Object result = declaredMethod.invoke(object, Arrays.stream(pythonObjects)
                    .map(this::unbox)
                    .collect(Collectors.toList()));
            return new PythonObject(result);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public PythonObject invokeOperator(String operator, PythonObject other) {
        Object thisUnboxed = unbox(this);
        Object otherUnboxed = unbox(other);
        if (thisUnboxed instanceof Double || otherUnboxed instanceof Double) {
            Double var1 = ((Number) thisUnboxed).doubleValue();
            Double var2 = ((Number) otherUnboxed).doubleValue();
            return new PythonObject(MathEvaluator.eval(operator, var1, var2));
        } else if (thisUnboxed instanceof Float || otherUnboxed instanceof Float) {
            Float var1 = ((Number) thisUnboxed).floatValue();
            Float var2 = ((Number) otherUnboxed).floatValue();
            return new PythonObject(MathEvaluator.eval(operator, var1, var2));
        } else if (thisUnboxed instanceof Long || otherUnboxed instanceof Long) {
            Long var1 = ((Number) thisUnboxed).longValue();
            Long var2 = ((Number) otherUnboxed).longValue();
            return new PythonObject(MathEvaluator.eval(operator, var1, var2));
        } else {
            Integer var1 = ((Number) thisUnboxed).intValue();
            Integer var2 = ((Number) otherUnboxed).intValue();
            return new PythonObject(MathEvaluator.eval(operator, var1, var2));
        }
    }

    public Object unbox(){
        return unbox(this);
    }

    private Object unbox(PythonObject pythonObject){
        Object unboxed = pythonObject.object;
        while(unboxed instanceof PythonObject){
            unboxed = ((PythonObject) unboxed).object;
        }
        return unboxed;
    }
}
