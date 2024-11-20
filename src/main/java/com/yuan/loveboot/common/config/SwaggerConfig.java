package com.yuan.loveboot.common.config;

import com.yuan.loveboot.common.enums.ResponseCode;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.customizers.GlobalOpenApiCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.AntPathMatcher;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Swagger配置
 *
 * @author Maverick
 */
@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {
    private final PermitResource permitResource;
    private final AntPathMatcher pathMatcher;

    @Bean
    public GroupedOpenApi userApi() {
        String[] paths = {"/**"};
        String[] packagedToMatch = {"com.yuan"};
        return GroupedOpenApi.builder().group("LoveBoot")
                .pathsToMatch(paths)
                .packagesToScan(packagedToMatch).build();
    }

    @Bean
    public OpenAPI customOpenAPI() {
        Contact contact = new Contact();
        contact.setName("Maverick");
        return new OpenAPI()
                .info(new Info()
                        .title("LoveBoot")
                        .description("LoveBoot")
                        .contact(contact)
                        .version("1.0"))
                .components(new Components()
                        .addSecuritySchemes(HttpHeaders.AUTHORIZATION,
                                new SecurityScheme()
                                        .name(HttpHeaders.AUTHORIZATION)
                                        .type(SecurityScheme.Type.APIKEY)
                                        .in(SecurityScheme.In.HEADER)
                                        .description("鉴权Token")
                        ));
    }

    /**
     * 全局自定义扩展
     * <p>
     * 在OpenAPI规范中，Operation 是一个表示 API 端点（Endpoint）或操作的对象。
     * 每个路径（Path）对象可以包含一个或多个 Operation 对象，用于描述与该路径相关联的不同 HTTP 方法（例如 GET、POST、PUT 等）。
     */
    @Bean
    public GlobalOpenApiCustomizer globalOpenApiCustomizer() {
        return openApi -> {
            // 获取所有路径和操作
            if (openApi.getPaths() != null) {
                List<String> permitList = permitResource.getPermitList();
                Set<String> permitSet = new HashSet<>(permitList);

                // 遍历所有路径
                openApi.getPaths().forEach((path, pathItem) -> {
                    boolean isPermitPath = permitSet.stream().anyMatch(permitPath -> pathMatcher.match(permitPath, path));

                    // 如果路径不在白名单中，添加鉴权参数
                    if (!isPermitPath) {
                        pathItem.readOperations().forEach(operation ->
                                operation.addSecurityItem(new SecurityRequirement().addList(HttpHeaders.AUTHORIZATION))
                        );
                    }

                    // 为每个操作设置自定义响应码
                    pathItem.readOperations().forEach(operation ->
                            customizeApiResponses(operation.getResponses())
                    );
                });
            }
        };
    }

    /**
     * 动态添加自定义响应码
     */
    private void customizeApiResponses(ApiResponses responses) {
        String ok = String.valueOf(HttpStatus.OK.value());
        Content content = responses.get(ok) != null ? responses.get(ok).getContent() : null;
        if (content == null) {
            return; // 如果没有OK状态码内容，则不进行操作
        }

        // 清空响应
        responses.clear();

        // 设置统一的ResponseCode枚举信息
        Arrays.stream(ResponseCode.values()).forEach(responseCode -> {
            ApiResponse apiResponse = new ApiResponse()
                    .description(responseCode.getMsg())
                    .content(content);
            responses.addApiResponse(String.valueOf(responseCode.getCode()), apiResponse);
        });
    }
}