package com.sky.annotation;

import com.sky.enumeration.OperationType;

import java.lang.annotation.*;

/**
 * @author LeLe
 * @date 2024/5/6 17:10
 * @Description:
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface AutoFill {
    /**
     * 指定操作类型
     * @return
     */
    OperationType value();
}
