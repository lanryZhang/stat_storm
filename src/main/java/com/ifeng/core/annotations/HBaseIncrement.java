/*
* HBaseIncrement.java 
* Created on  202016/10/21 13:15 
* Copyright © 2012 Phoenix New Media Limited All Rights Reserved 
*/
package com.ifeng.core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Class Description Here
 *
 * @author zhanglr
 * @version 1.0.1
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface HBaseIncrement {
    boolean value();
}
