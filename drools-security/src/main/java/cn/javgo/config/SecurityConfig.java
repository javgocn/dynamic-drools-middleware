package cn.javgo.config;

import cn.javgo.config.properties.IgnoreUrlsProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import javax.annotation.Resource;

/**
 * Spring Security 配置类
 */
@Configuration
@EnableWebSecurity  // 开启 Spring Security 功能
public class SecurityConfig {

    /**
     * 白名单配置
     */
    @Resource
    private IgnoreUrlsProperties ignoreUrlsProperties;
}
