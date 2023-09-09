package cn.javgo.drools.domain;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Swagger 自定义配置
 */
@Data
@EqualsAndHashCode
@Builder
public class SwaggerProperties {

    // API 文档生成基础路径（即控制层所在的包）
    private String apiBasePackage;

    // 是否要启用登录认证
    private boolean enableSecurity;

    private String title;

    private String description;

    private String version;

    private String contactName;

    private String contactUrl;

    private String contactEmail;
}
