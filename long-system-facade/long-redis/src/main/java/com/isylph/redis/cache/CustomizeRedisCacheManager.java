package com.isylph.redis.cache;

import com.isylph.redis.utils.RedisSerializerUtils;
import com.isylph.utils.StringUtils;
import org.springframework.cache.Cache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

public class CustomizeRedisCacheManager extends RedisCacheManager {

    private static final String SEPARATOR = "#";
    private RedisCacheWriter redisCacheWriter;

    public CustomizeRedisCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration) {
        super(cacheWriter, defaultCacheConfiguration);
        this.redisCacheWriter = cacheWriter;
    }

    public CustomizeRedisCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration, String... initialCacheNames) {
        super(cacheWriter, defaultCacheConfiguration, initialCacheNames);
        this.redisCacheWriter = cacheWriter;
    }

    public Cache getCache(String name) {
        String[] cacheParams = name.split("#");
        String cacheName = cacheParams[0];
        if (StringUtils.isEmpty(cacheName)) {
            return null;
        } else if (cacheParams.length == 1) {
            return super.getCache(name);
        } else {
            RedisSerializer<String> redisSerializer = new StringRedisSerializer();
            Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = RedisSerializerUtils.getJackson2JsonRedisSerializer();
            RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(Long.valueOf(cacheParams[1]))).serializeKeysWith(SerializationPair.fromSerializer(redisSerializer)).serializeValuesWith(SerializationPair.fromSerializer(jackson2JsonRedisSerializer));
            return new CustomizeRedisCache(cacheName, this.redisCacheWriter, config);
        }
    }
}
