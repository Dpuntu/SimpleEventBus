package com.dpuntu.uibus;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created on 2017/11/2.
 *
 * @author dpuntu
 */
@Documented
@Target( {ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EventThread {
}
