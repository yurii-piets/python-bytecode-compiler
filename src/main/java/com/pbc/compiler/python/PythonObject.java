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
                    .map(PythonObject::getObject)
                    .collect(Collectors.toList()));
            return new PythonObject(result);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public PythonObject invokeOperator(String operator, PythonObject other) {
        if (object instanceof Double || other.object instanceof Double) {
            Double var1 = ((Number) object).doubleValue();
            Double var2 = ((Number) other.object).doubleValue();
            return new PythonObject(MathEvaluator.eval(operator, var1, var2));
        } else if (object instanceof Float || other.object instanceof Float) {
            Float var1 = ((Number) object).floatValue();
            Float var2 = ((Number) other.object).floatValue();
            return new PythonObject(MathEvaluator.eval(operator, var1, var2));
        } else if (object instanceof Long || other.object instanceof Long) {
            Long var1 = ((Number) object).longValue();
            Long var2 = ((Number) other.object).longValue();
            return new PythonObject(MathEvaluator.eval(operator, var1, var2));
        } else {
            Integer var1 = ((Number) object).intValue();
            Integer var2 = ((Number) other.object).intValue();
            return new PythonObject(MathEvaluator.eval(operator, var1, var2));
        }
    }
}
