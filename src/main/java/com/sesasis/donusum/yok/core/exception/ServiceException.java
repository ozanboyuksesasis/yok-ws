package com.sesasis.donusum.yok.core.exception;

public class ServiceException extends RuntimeException {

    private String className;
    private String methodName;
    private Object fieldValue;

    public ServiceException( String className, String methodName, Object fieldValue) {
        super(String.format("ServiceException : %s - %s : '%s'", className, methodName, fieldValue));
        this.className = className;
        this.methodName = methodName;
        this.fieldValue = fieldValue;
    }

    public String getClassName() {
        return className;
    }

    public String getMethodName() {
        return methodName;
    }

    public Object getFieldValue() {
        return fieldValue;
    }
}
