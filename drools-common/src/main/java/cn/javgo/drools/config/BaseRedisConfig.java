package cn.javgo.drools.config;

import cn.javgo.drools.service.RedisService;
import cn.javgo.drools.service.impl.RedisServiceImpl;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import java.time.Duration;

/**
 * Redis 基础配置类：设置序列化方式、缓存管理器
 */
public class BaseRedisConfig {

    @Bean
    public RedisService redisService(){
        return new RedisServiceImpl();
    }

    /**
     * 自定义 RedisTemplate 模板类的序列化规则
     *
     * @param factory Redis 连接工厂
     * @return RedisTemplate 模板类
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory){
        // 创建 Redis 序列化器
        RedisSerializer<Object> redisSerializer = redisSerializer();
        RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();

        // 设置 Redis 连接工厂
        redisTemplate.setConnectionFactory(factory);

        // 设置 key 的序列化方式为字符串序列化
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        // 设置 value 的序列化方式
        redisTemplate.setValueSerializer(redisSerializer);

        // 设置 hash key 的序列化方式为字符串序列化
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        // 设置 hash value 的序列化方式
        redisTemplate.setHashValueSerializer(redisSerializer);

        // 初始化 RedisTemplate 属性
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    /**
     * 自定义 Redis 序列化器
     *
     * @return Redis 序列化器
     */
    @Bean
    public RedisSerializer<Object> redisSerializer(){
        // 使用 Jackson2JsonRedisSerializer 进行 JSON 序列化
        Jackson2JsonRedisSerializer<Object> jsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();

        // 设置对象映射器的可见性：任何属性都可以进行序列化和反序列化
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // 设置对象映射器的默认类型：非 final 修饰的任何类型都可以进行序列化和反序列化
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);

        // 设置对象映射器的序列化方式
        jsonRedisSerializer.setObjectMapper(objectMapper);
        return jsonRedisSerializer;
    }

    /**
     * 自定义 Redis 缓存管理器
     *
     * @param factory Redis 连接工厂
     * @return Redis 缓存管理器
     */
    @Bean
    public RedisCacheManager redisCacheManager(RedisConnectionFactory factory){
        // 创建 Redis 缓存写入器
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(factory);

        // 创建 Redis 缓存配置
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                // 设置值的序列化方式
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer()))
                // 设置缓存条目的默认生存时间为 1 天
                .entryTtl(Duration.ofDays(1));

        // 返回新的 Redis 缓存管理器
        return new RedisCacheManager(redisCacheWriter,redisCacheConfiguration);
    }
}
