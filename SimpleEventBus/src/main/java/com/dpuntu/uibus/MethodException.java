package com.dpuntu.uibus;

/**
 * Created on 2017/11/2.
 *
 * @author dpuntu
 */

public class MethodException extends RuntimeException {
    public MethodException() {
        super();
    }

    public MethodException(String errorMsg) {
        super(errorMsg);
    }
}
