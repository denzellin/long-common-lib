package com.isylph.redis.application;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.core.types.Expiration;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Slf4j
public class DistributedLock implements AutoCloseable  {

    private RedisTemplate redisTemplate;
    private String key;
    private String value;
    //单位：秒
    private int expireTime;


    public DistributedLock(RedisTemplate redisTemplate, String key, int expireTime){
        this.redisTemplate = redisTemplate;
        this.key = key;
        this.expireTime=expireTime;
        this.value = UUID.randomUUID().toString();
    }


    @Override
    public void close() throws Exception {
        unLock();
    }


    /**
     * 获取分布式锁
     * SET resource_name my_random_value NX PX 30000
     * 每一个线程对应的随机值 my_random_value 不一样，用于释放锁的时候校验
     * NX 表示 key 不存在的时候成功，key 存在的时候设置不成功，Redis 自己是单线程，串行执行的，第一个执行的才可以设置成功
     * PX 表示过期时间，没有设置的话，忘记删除，就会永远不过期
     */
    public boolean getLock(){

        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        RedissonClient redisson = Redisson.create(config);
        redisson.getLock("localLock");

        RedisCallback<Boolean> redisCallback = connection -> {
            //设置NX
            RedisStringCommands.SetOption setOption = RedisStringCommands.SetOption.ifAbsent();

            //设置过期时间
            Expiration expiration = Expiration.seconds(expireTime);

            //序列化key
            byte[] redisKey = redisTemplate.getKeySerializer().serialize(key);

            //序列化value
            byte[] redisValue = redisTemplate.getValueSerializer().serialize(value);

            //执行setnx操作
            Boolean result = connection.set(redisKey, redisValue, expiration, setOption);
            return result;
        };

        //获取分布式锁
        Boolean lock = (Boolean)redisTemplate.execute(redisCallback);
        return lock;
    }

    /**
     * 释放锁的时候随机数相同的时候才可以释放，避免释放了别人设置的锁（自己的已经过期了所以别人才可以设置成功）
     * 释放的时候采用 LUA 脚本，因为 delete 没有原生支持删除的时候校验值，证明是当前线程设置进去的值
     * 脚本是在官方文档里面有的
     */
    public boolean unLock() {
        // key 是自己才可以释放，不是就不能释放别人的锁
        String script = "if redis.call(\"get\",KEYS[1]) == ARGV[1] then\n" +
                "    return redis.call(\"del\",KEYS[1])\n" +
                "else\n" +
                "    return 0\n" +
                "end";
        RedisScript<Boolean> redisScript = RedisScript.of(script,Boolean.class);
        List<String> keys = Arrays.asList(key);

        // 执行脚本的时候传递的 value 就是对应的值
        Boolean result = (Boolean)redisTemplate.execute(redisScript, keys, value);
        log.info("释放锁的结果："+result);
        return result;
    }
}
