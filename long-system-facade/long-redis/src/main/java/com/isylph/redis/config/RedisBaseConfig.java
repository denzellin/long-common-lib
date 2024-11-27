package com.isylph.redis.config;

import com.isylph.redis.application.RedisTemplateUtils;
import com.isylph.redis.cache.CustomizeRedisCacheManager;
import com.isylph.redis.utils.RedisSerializerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.autoconfigure.cache.CacheProperties.Redis;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableConfigurationProperties({CacheProperties.class})
@EnableCaching
public class RedisBaseConfig {

    @Autowired
    private CacheProperties cacheProperties;

    public RedisBaseConfig() {
    }


    @Bean
    @DependsOn({"redisTemplate"})
    public RedisTemplateUtils redisTemplateUtils(RedisTemplate redisTemplate) {
        return new RedisTemplateUtils(redisTemplate);
    }

    /*

    @Bean
    LettuceConnectionFactory lettuceConnectionFactory() {
        // 连接池配置
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setMaxIdle(maxIdle == null ? 8 : maxIdle);
        poolConfig.setMinIdle(minIdle == null ? 1 : minIdle);
        poolConfig.setMaxTotal(maxTotal == null ? 8 : maxTotal);
        poolConfig.setMaxWaitMillis(maxWaitMillis == null ? 5000L : maxWaitMillis);
        LettucePoolingClientConfiguration lettucePoolingClientConfiguration = LettucePoolingClientConfiguration.builder()
                .poolConfig(poolConfig)
                .build();
        // 单机redis
        RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
        redisConfig.setHostName(host==null||"".equals(host)?"localhost":host.split(":")[0]);
        redisConfig.setPort(Integer.valueOf(host==null||"".equals(host)?"6379":host.split(":")[1]));
        if (password != null && !"".equals(password)) {
            redisConfig.setPassword(password);
        }

        // 哨兵redis
        // RedisSentinelConfiguration redisConfig = new RedisSentinelConfiguration();

        // 集群redis
        */
/*RedisClusterConfiguration redisConfig = new RedisClusterConfiguration();
        Set<RedisNode> nodeses = new HashSet<>();
        String[] hostses = nodes.split("-");
        for (String h : hostses) {
            h = h.replaceAll("\\s", "").replaceAll("\n", "");
            if (!"".equals(h)) {
                String host = h.split(":")[0];
                int port = Integer.valueOf(h.split(":")[1]);
                nodeses.add(new RedisNode(host, port));
            }
        }
        redisConfig.setClusterNodes(nodeses);
        // 跨集群执行命令时要遵循的最大重定向数量
        redisConfig.setMaxRedirects(3);
        redisConfig.setPassword(password);*//*


        return new LettuceConnectionFactory(redisConfig, lettucePoolingClientConfiguration);
    }



    @Bean
    public RedisTemplate<String, Serializable> redisTemplate(LettuceConnectionFactory connectionFactory) {
        RedisTemplate<String, Serializable> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setConnectionFactory(connectionFactory);
        return redisTemplate;
    }
*/


    @Bean
    @ConditionalOnMissingBean({RedisTemplate.class})
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> customRedisTemplate = new RedisTemplate();
        customRedisTemplate.setConnectionFactory(redisConnectionFactory);
        RedisSerializer<String> stringSerializer = new StringRedisSerializer();
        customRedisTemplate.setStringSerializer(stringSerializer);
        customRedisTemplate.setDefaultSerializer(stringSerializer);
        customRedisTemplate.setKeySerializer(stringSerializer);
        customRedisTemplate.setValueSerializer(RedisSerializerUtils.getJackson2JsonRedisSerializer());
        customRedisTemplate.setHashKeySerializer(stringSerializer);
        customRedisTemplate.setHashValueSerializer(RedisSerializerUtils.getJackson2JsonRedisSerializer());
        customRedisTemplate.afterPropertiesSet();
        return customRedisTemplate;
    }

    @Bean
    @ConditionalOnMissingBean({RedisCacheWriter.class})
    public RedisCacheWriter redisCacheWriter(RedisConnectionFactory redisConnectionFactory) {
        return RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory);
    }

    @Bean
    @ConditionalOnMissingBean({RedisCacheManager.class})
    public RedisCacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig().serializeKeysWith(SerializationPair.fromSerializer(new StringRedisSerializer())).serializeValuesWith(SerializationPair.fromSerializer(RedisSerializerUtils.getJackson2JsonRedisSerializer()));
        Redis redis = this.cacheProperties.getRedis();
        if (redis.getTimeToLive() != null) {
            config = config.entryTtl(redis.getTimeToLive());
        }

        if (redis.getKeyPrefix() != null) {
            config = config.prefixCacheNameWith(redis.getKeyPrefix());
        }

        if (!redis.isCacheNullValues()) {
            config = config.disableCachingNullValues();
        }

        if (!redis.isUseKeyPrefix()) {
            config = config.disableKeyPrefix();
        }

        return new CustomizeRedisCacheManager(this.redisCacheWriter(redisConnectionFactory), config);
    }

}
