package cn.javgo.drools.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 全局跨域配置
 */
@Configuration
public class GlobalCorsConfig {

    /**
     * 配置跨域访问的过滤器
     * @return 返回配置好的跨域过滤器
     */
    @Bean
    public CorsFilter corsFilter(){
        // 创建 CORS 配置对象
        CorsConfiguration configuration = new CorsConfiguration();
        // 允许任何域名使用
        configuration.addAllowedOrigin("*");
        // 允许客户端携带凭证（例如 cookie、session、token 等）
        configuration.setAllowCredentials(true);
        // 允许所有的请求头（主要是 JWT 需要添加的 Authorization 头）
        configuration.addAllowedHeader("*");
        // 允许所有的请求方法（主要是跨域的 OPTIONS 预检请求）
        configuration.addAllowedMethod("*");

        // 创建 CORS 配置源对象
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // 为所有的 URL 路径设置跨域配置
        source.registerCorsConfiguration("/**",configuration);
        // 返回新的 CORS 过滤器，使用上面的配置源
        return new CorsFilter(source);
    }
}
