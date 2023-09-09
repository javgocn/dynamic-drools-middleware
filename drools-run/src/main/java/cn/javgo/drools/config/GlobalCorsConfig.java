package cn.javgo.drools.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
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
        CorsConfiguration configuration = new CorsConfiguration();
        return null;
    }
}
