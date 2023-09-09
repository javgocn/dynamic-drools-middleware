package cn.javgo.drools.config;

import cn.javgo.drools.domain.SwaggerProperties;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger API 文档相关配置
 */
@Configuration
@EnableSwagger2 // 开启 Swagger
public class SwaggerConfig extends BaseSwaggerConfig{

    @Override
    public SwaggerProperties swaggerProperties() {
        return SwaggerProperties.builder()
                .apiBasePackage("cn.javgo.drools.controller") // 扫描的 Controller 包
                .title("Drools 业务流程管理系统运行态") // 文档标题
                .description("Drools 业务流程管理系统运行态相关接口文档") // 文档描述
                .contactName("javgo") // 联系人姓名
                .version("1.0") // 版本号
                .enableSecurity(false) // 是否需要配置 JWT 登录认证(暂时不需要)
                .build();
    }
}
