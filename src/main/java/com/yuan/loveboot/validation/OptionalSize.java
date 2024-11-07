package com.yuan.loveboot.validation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * 自定义校验注解，用于校验字符串的长度、是否允许空格。
 *
 * @author Maverick
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = OptionalSizeValidator.class)
@Documented
public @interface OptionalSize {

    /**
     * 错误消息，当校验失败时返回该消息。
     */
    String message() default "参数不合规";

    /**
     * 字符串的最小长度（默认 0）
     */
    int min() default 0;

    /**
     * 字符串的最大长度（默认 Integer 最大值）。
     */
    int max() default Integer.MAX_VALUE;

    /**
     * 字符串能否包含空格
     */
    boolean allowSpaces() default false;

    /**
     * 用于分组校验。
     */
    Class<?>[] groups() default {};

    /**
     * 用于携带元数据的负载。
     */
    Class<? extends Payload>[] payload() default {};
}