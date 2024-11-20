package com.yuan.loveboot.common.validation;

import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.oas.models.media.Schema;
import org.springdoc.core.customizers.PropertyCustomizer;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * 自定义参数描述器，用于在 Swagger 中显示自定义校验注解的信息。
 *
 * @author Maverick
 */
@Component
public class OptionalSizePropertyCustomizer implements PropertyCustomizer {
    /**
     * 自定义 Swagger 参数描述，加入 OptionalSize 注解的详细信息。
     * 根据 {@link OptionalSize} 注解的配置，向 Swagger 参数描述中添加详细校验信息。
     */
    @Override
    public Schema<?> customize(Schema property, AnnotatedType type) {
        // 检查字段是否包含 @OptionalSize 注解
        OptionalSize optionalSize = type.getCtxAnnotations() != null
                ? (OptionalSize) Arrays.stream(type.getCtxAnnotations())
                .filter(OptionalSize.class::isInstance)
                .findFirst()
                .orElse(null)
                : null;

        if (optionalSize != null) {
            // 如果 min 和 max 被设置，将其映射到 Schema 的 minLength 和 maxLength
            property.setMinLength(optionalSize.min());
            property.setMaxLength(optionalSize.max());

            String description = property.getDescription() != null ? property.getDescription() + "，" : "";
            description += "可包含空格： " + optionalSize.allowSpaces();
            property.setDescription(description);
        }
        return property;
    }
}
