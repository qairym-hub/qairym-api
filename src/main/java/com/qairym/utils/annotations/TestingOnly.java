package com.qairym.utils.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A method annotated {@code @TestingOnly} is designed 
 * only for developing purposes.
 * 
 * <p>It's strongly recommended to annotate such methods
 * in order to define the production designed from 
 * testing.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface TestingOnly {
    
}
