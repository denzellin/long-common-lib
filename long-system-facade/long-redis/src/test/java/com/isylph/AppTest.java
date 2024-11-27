package com.isylph;


import com.isylph.redis.application.DistributedLock;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Unit test for simple App.
 */
@Slf4j
public class AppTest
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        RedisTemplate redisTemplate = new RedisTemplate();
        // 每次获取的时候，自己线程需要new对应的RedisLock：
        log.info("我进入了方法！");
        try (DistributedLock redisLock = new DistributedLock(redisTemplate,"redisKey",30)){
            if (redisLock.getLock()) {
                log.info("我进入了锁！！");
                Thread.sleep(15000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("方法执行完成");
        return;
    }
}
