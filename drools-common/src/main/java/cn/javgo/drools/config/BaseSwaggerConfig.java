package cn.javgo.drools.config;

import cn.javgo.drools.domain.SwaggerProperties;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.spring.web.plugins.WebFluxRequestHandlerProvider;
import springfox.documentation.spring.web.plugins.WebMvcRequestHandlerProvider;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Swagger 基础配置类（抽象基类）
 */
public abstract class BaseSwaggerConfig {

    /**
     * 抽象方法，子类需要提供具体的 Swagger 属性实现
     * @return SwaggerProperties 对象
     */
    public abstract SwaggerProperties swaggerProperties();

    /**
     * 创建 Docket 对象，用于 Swagger 的主要配置
     * @return Docket 对象
     */
    @Bean
    public Docket createRestApi() {
        // 获取 Swagger 属性
       SwaggerProperties swaggerProperties = swaggerProperties();

        // 创建 Docket 对象并配置其属性
       Docket docket =  new Docket(DocumentationType.SWAGGER_2)
               // 返回一个 ApiSelectorBuilder 实例，用于控制哪些接口暴露给 Swagger
               .select()
               // 设置扫描的 API 的包路径
               .apis(RequestHandlerSelectors.basePackage(swaggerProperties.getApiBasePackage()))
               // 扫描所有路径
               .paths(PathSelectors.any())
               .build()
               // 设置 API 的基本信息
               .apiInfo(apiInfo(swaggerProperties));

        // 如果启用了安全验证，则需要配置以下内容（即是否启用了 JWT）
       if (swaggerProperties.isEnableSecurity())
           // 配置认证方式和认证场景
           docket.securitySchemes(securitySchemes()).securityContexts(securityContexts("/*/.*"));

       return docket;
    }

    /**
     * 创建 API 的基本信息
     * @param swaggerProperties Swagger 属性
     * @return ApiInfo 对象
     */
    private ApiInfo apiInfo(SwaggerProperties swaggerProperties) {
        return new ApiInfoBuilder()
                .title(swaggerProperties.getTitle()) // 设置标题
                .description(swaggerProperties.getDescription()) // 设置描述
                .contact(new Contact( // 设置联系方式
                        swaggerProperties.getContactName(),
                        swaggerProperties.getContactUrl(),
                        swaggerProperties.getContactEmail()))
                .version(swaggerProperties.getVersion())  // 设置版本
                .build();
    }

    /**
     * 创建安全验证的配置
     * @return 安全验证的配置列表
     */
    private List<SecurityScheme> securitySchemes(){

        List<SecurityScheme> list = new ArrayList<>();

        // 创建一个 ApiKey 对象，表示在请求头中的 "Authorization" 字段中携带 token
        ApiKey apiKey = new ApiKey("Authorization","Authorization","header");
        list.add(apiKey);
        return list;
    }

    /**
     * 创建安全上下文的配置
     * @param path 路径匹配模式
     * @return 安全上下文的配置列表
     */
    private List<SecurityContext> securityContexts(String path){
        List<SecurityContext> result = new ArrayList<>();
        SecurityContext securityContext = SecurityContext.builder()
                .securityReferences(defaultAuth()) // 设置默认的安全引用
                .operationSelector(o -> o.requestMappingPattern().matches(path)) // 设置路径匹配模式（匹配所有路径）
                .build();
        result.add(securityContext);
        return result;
    }

    /**
     * 创建默认的安全引用
     * @return 安全引用列表
     */
    private List<SecurityReference> defaultAuth(){
        List<SecurityReference> result = new ArrayList<>();
        // 创建一个全局的授权范围
        AuthorizationScope authorizationScope = new AuthorizationScope("global","accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        // 创建一个安全引用，表示需要在 "Authorization" 字段中携带 token
        SecurityReference securityReference = new SecurityReference("Authorization",authorizationScopes);
        result.add(securityReference);
        return result;
    }

    /*public BeanPostProcessor generateBeanPostProcessor(){
        return new BeanPostProcessor() {
            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                if (bean instanceof WebMvcRequestHandlerProvider || bean instanceof WebFluxRequestHandlerProvider){
                    customizeSpringfoxHandlerMappings(getHandlerMappings(bean));
                }
                return bean;
            }

            private <T extends RequestMappingInfoHandlerMapping> void customizeSpringfoxHandlerMappings(List<T> mappings){
                List<T> copy = mappings.stream()
                        .filter(mapping -> mapping.getPatternParser() == null)
                        .collect(Collectors.toList());
                mappings.clear();
                mappings.addAll(copy);
            }

            private List<RequestMappingInfoHandlerMapping> getHandlerMappings(Object bean){
                try{
                    Field field = ReflectionUtils.findField(bean.getClass(), "handlerMappings");
                    field.setAccessible(true);
                    return (List<RequestMappingInfoHandlerMapping>) field.get(bean);
                }catch (IllegalArgumentException | IllegalAccessException e) {
                    throw new IllegalStateException(e);
                }
            }
        };
    }*/
}
