package com.dpuntu.uibus;

/**
 * Created on 2017/11/2.
 *
 * @author dpuntu
 */

public class ClassMethod {
    private String methodName;
    private Object Cls;

    public ClassMethod(String methodName, Object cls) {
        this.methodName = methodName;
        Cls = cls;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Object getCls() {
        return Cls;
    }

    public void setCls(Object cls) {
        Cls = cls;
    }
}
