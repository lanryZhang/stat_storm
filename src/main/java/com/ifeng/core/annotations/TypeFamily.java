package com.ifeng.core.annotations;

import java.lang.annotation.*;

/**
 * Created by zhanglr on 2016/10/12.
 */
@Inherited
@Target(value = ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface TypeFamily {
    String value();
}
