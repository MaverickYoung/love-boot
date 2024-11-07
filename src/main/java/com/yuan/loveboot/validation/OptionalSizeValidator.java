package com.yuan.loveboot.validation;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * 自定义校验逻辑实现类，用于校验字符串的长度、是否包含空格。
 *
 * @author Maverick
 */
public class OptionalSizeValidator implements ConstraintValidator<OptionalSize, String> {
    private int min;
    private int max;
    private boolean allowSpaces;

    /**
     * 初始化方法，用于从注解中获取参数。
     */
    @Override
    public void initialize(OptionalSize constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
        this.allowSpaces = constraintAnnotation.allowSpaces();
    }

    /**
     * 校验逻辑，根据参数校验字符串。
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // 检查是否允许包含空格
        if (!allowSpaces && value.contains(" ")) {
            return false;
        }

        // 检查字符串长度
        int length = value.length();
        return length >= min && length <= max;
    }
}


