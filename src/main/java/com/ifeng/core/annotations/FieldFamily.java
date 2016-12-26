package com.ifeng.core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Target;

/**
 * Created by zhanglr on 2016/10/12.
 */
@Inherited
@Target(value = ElementType.FIELD)
public @interface FieldFamily {
    String value();
}
