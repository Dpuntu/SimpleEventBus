package com.dpuntu.uibus;

/**
 * Created on 2017/11/2.
 *
 * @author dpuntu
 */

public class ObjMethod {
    private String methodName;
    private Object methodType;

    public ObjMethod(String methodName, Object methodType) {
        this.methodName = methodName;
        this.methodType = methodType;
    }

    public Object getMethodType() {
        return methodType;
    }

    public void setMethodType(Object methodType) {
        this.methodType = methodType;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
}
