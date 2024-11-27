package com.isylph.redis.application;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class RedisTemplateUtils {

    private RedisTemplate<Object, Object> redisTemplate;

    public RedisTemplateUtils(RedisTemplate<Object, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public RedisTemplate<Object, Object> getInstance() {
        return this.redisTemplate;
    }

    public void set(String key, Object value) {
        this.redisTemplate.opsForValue().set(key, value);
    }

    public Object get(String key) {
        Object obj = this.redisTemplate.opsForValue().get(key);
        return obj;
    }

    public boolean hasKey(String key){
        return redisTemplate.hasKey(key);
    }

    public void setForTimeMS(String key, Object value, long time) {
        this.redisTemplate.opsForValue().set(key, value, time, TimeUnit.MILLISECONDS);
    }

    public void setForTimeMIN(String key, Object value, long time) {
        this.redisTemplate.opsForValue().set(key, value, time, TimeUnit.MINUTES);
    }

    public void setForTimeCustom(String key, Object value, long time, TimeUnit type) {
        this.redisTemplate.opsForValue().set(key, value, time, type);
    }

    public Object getAndSet(String key, Object value) {
        Object obj = this.redisTemplate.opsForValue().getAndSet(key, value);
        return obj == null ? null : obj;
    }

    public void batchSet(Map<String, String> keyAndValue) {
        this.redisTemplate.opsForValue().multiSet(keyAndValue);
    }

    public void batchSetIfAbsent(Map<String, String> keyAndValue) {
        this.redisTemplate.opsForValue().multiSetIfAbsent(keyAndValue);
    }

    public Long increment(String key, long number) {
        return this.redisTemplate.opsForValue().increment(key, number);
    }

    public Double increment(String key, double number) {
        return this.redisTemplate.opsForValue().increment(key, number);
    }

    public boolean expire(String key, long time, TimeUnit type) {
        return this.redisTemplate.boundValueOps(key).expire(time, type);
    }

    public boolean persist(String key) {
        return this.redisTemplate.boundValueOps(key).persist();
    }

    public Long getExpire(String key) {
        return this.redisTemplate.boundValueOps(key).getExpire();
    }

    public void rename(String key, String newKey) {
        this.redisTemplate.boundValueOps(key).rename(newKey);
    }

    public boolean delete(String key) {
        return this.redisTemplate.delete(key);
    }

    public void put(String key, String hashKey, Object value) {
        this.redisTemplate.opsForHash().put(key, hashKey, value);
    }

    public void putAll(String key, Map<String, String> map) {
        this.redisTemplate.opsForHash().putAll(key, map);
    }

    public boolean putIfAbsent(String key, String hashKey, String value) {
        return this.redisTemplate.opsForHash().putIfAbsent(key, hashKey, value);
    }

    public Long delete(String key, String... hashKeys) {
        return this.redisTemplate.opsForHash().delete(key, hashKeys);
    }

    public Long increment(String key, String hashKey, long number) {
        return this.redisTemplate.opsForHash().increment(key, hashKey, number);
    }

    public Double increment(String key, String hashKey, Double number) {
        return this.redisTemplate.opsForHash().increment(key, hashKey, number);
    }

    public Object getHashKey(String key, String hashKey) {
        return this.redisTemplate.opsForHash().get(key, hashKey);
    }

    public Map<Object, Object> getHashEntries(String key) {
        return this.redisTemplate.opsForHash().entries(key);
    }

    public boolean hashKey(String key, String hashKey) {
        return this.redisTemplate.opsForHash().hasKey(key, hashKey);
    }

    public Set<Object> hashKeys(String key) {
        return this.redisTemplate.opsForHash().keys(key);
    }

    public Long hashSize(String key) {
        return this.redisTemplate.opsForHash().size(key);
    }

    public Long leftPush(String key, Object value) {
        return this.redisTemplate.opsForList().leftPush(key, value);
    }

    public Object leftPop(String key) {
        return this.redisTemplate.opsForList().leftPop(key);
    }

    public Object leftPop(String key, long timeout, TimeUnit unit) {
        return this.redisTemplate.opsForList().leftPop(key, timeout, unit);
    }

    public Long leftPushAll(String key, Collection<Object> values) {
        return this.redisTemplate.opsForList().leftPushAll(key, values);
    }

    public Long rightPush(String key, Object value) {
        return this.redisTemplate.opsForList().rightPush(key, value);
    }

    public Object rightPop(String key, long timeout, TimeUnit unit) {
        return this.redisTemplate.opsForList().rightPop(key, timeout, unit);
    }

    public Object rightPop(String key) {
        return this.redisTemplate.opsForList().rightPop(key);
    }

    public Long rightPushAll(String key, Collection<Object> values) {
        return this.redisTemplate.opsForList().rightPushAll(key, values);
    }

    public Object popIndex(String key, long index) {
        return this.redisTemplate.opsForList().index(key, index);
    }

    public Long listSize(String key) {
        return this.redisTemplate.opsForList().size(key);
    }

    public List<Object> listRange(String key, long start, long end) {
        return this.redisTemplate.opsForList().range(key, start, end);
    }

    public Long listRemove(String key, long count, Object value) {
        return this.redisTemplate.opsForList().remove(key, count, value);
    }

    public void listTrim(String key, long start, long end) {
        this.redisTemplate.opsForList().trim(key, start, end);
    }

    public Object rightPopAndLeftPush(String key, String key2) {
        return this.redisTemplate.opsForList().rightPopAndLeftPush(key, key2);
    }

    public Long add(String key, Object... values) {
        return this.redisTemplate.opsForSet().add(key, values);
    }

    public Set<Object> difference(String key, String otherkey) {
        return this.redisTemplate.opsForSet().difference(key, otherkey);
    }

    public Set<Object> difference(String key, Object otherkey) {
        return this.redisTemplate.opsForSet().difference(key, otherkey);
    }

    public Set<Object> difference(String key, Collection<Object> otherKeys) {
        return this.redisTemplate.opsForSet().difference(key, otherKeys);
    }

    public Long differenceAndStore(String key, String otherkey, String newKey) {
        return this.redisTemplate.opsForSet().differenceAndStore(key, otherkey, newKey);
    }

    public Long differenceAndStore(String key, Collection<Object> otherKeys, String newKey) {
        return this.redisTemplate.opsForSet().differenceAndStore(newKey, otherKeys, newKey);
    }

    public Long remove(String key, Object... values) {
        return this.redisTemplate.opsForSet().remove(key, values);
    }

    public Object randomSetPop(String key) {
        return this.redisTemplate.opsForSet().pop(key);
    }

    public Object randomSet(String key) {
        return this.redisTemplate.opsForSet().randomMember(key);
    }

    public List<Object> randomSet(String key, long count) {
        return this.redisTemplate.opsForSet().randomMembers(key, count);
    }

    public Set<Object> randomSetDistinct(String key, long count) {
        return this.redisTemplate.opsForSet().distinctRandomMembers(key, count);
    }

    public boolean moveSet(String key, Object value, String destKey) {
        return this.redisTemplate.opsForSet().move(key, value, destKey);
    }

    public Long setSize(String key) {
        return this.redisTemplate.opsForSet().size(key);
    }

    public boolean isMember(String key, Object value) {
        return this.redisTemplate.opsForSet().isMember(key, value);
    }

    public Set<Object> unionSet(String key, String otherKey) {
        return this.redisTemplate.opsForSet().union(key, otherKey);
    }

    public Set<Object> unionSet(String key, Collection<Object> otherKeys) {
        return this.redisTemplate.opsForSet().union(key, otherKeys);
    }

    public Long unionAndStoreSet(String key, String otherKey, String destKey) {
        return this.redisTemplate.opsForSet().unionAndStore(key, otherKey, destKey);
    }

    public Long unionAndStoreSet(String key, Collection<Object> otherKeys, String destKey) {
        return this.redisTemplate.opsForSet().unionAndStore(key, otherKeys, destKey);
    }

    public Set<Object> members(String key) {
        return this.redisTemplate.opsForSet().members(key);
    }

    public boolean add(String key, Object value, double score) {
        return this.redisTemplate.opsForZSet().add(key, value, score);
    }

    public Long batchAddZset(String key, Set<TypedTuple<Object>> tuples) {
        return this.redisTemplate.opsForZSet().add(key, tuples);
    }

    public Long removeZset(String key, Object... values) {
        return this.redisTemplate.opsForZSet().remove(key, values);
    }

    public Double incrementScore(String key, Object value, double score) {
        return this.redisTemplate.opsForZSet().incrementScore(key, value, score);
    }

    public Long rank(String key, Object value) {
        return this.redisTemplate.opsForZSet().rank(key, value);
    }

    public Long reverseRank(String key, Object value) {
        return this.redisTemplate.opsForZSet().reverseRank(key, value);
    }

    public Set<TypedTuple<Object>> rangeWithScores(String key, long start, long end) {
        return this.redisTemplate.opsForZSet().rangeWithScores(key, start, end);
    }

    public Set<Object> range(String key, long start, long end) {
        return this.redisTemplate.opsForZSet().range(key, start, end);
    }

    public Set<Object> rangeByScore(String key, double min, double max) {
        return this.redisTemplate.opsForZSet().rangeByScore(key, min, max);
    }

    public Set<TypedTuple<Object>> rangeByScoreWithScores(String key, double min, double max) {
        return this.redisTemplate.opsForZSet().rangeByScoreWithScores(key, min, max);
    }

    public Set<Object> rangeByScore(String key, double min, double max, long offset, long count) {
        return this.redisTemplate.opsForZSet().rangeByScore(key, min, max, offset, count);
    }

    public Set<TypedTuple<Object>> rangeByScoreWithScores(String key, double min, double max, long offset, long count) {
        return this.redisTemplate.opsForZSet().rangeByScoreWithScores(key, min, max, offset, count);
    }

    public Set<Object> reverseRange(String key, long start, long end) {
        return this.redisTemplate.opsForZSet().reverseRange(key, start, end);
    }

    public Set<TypedTuple<Object>> reverseRangeWithScores(String key, long start, long end) {
        return this.redisTemplate.opsForZSet().reverseRangeWithScores(key, start, end);
    }

    public Set<Object> reverseRangeByScore(String key, double min, double max) {
        return this.redisTemplate.opsForZSet().reverseRangeByScore(key, min, max);
    }

    public Set<TypedTuple<Object>> reverseRangeByScoreWithScores(String key, double min, double max) {
        return this.redisTemplate.opsForZSet().reverseRangeByScoreWithScores(key, min, max);
    }

    public Set<Object> reverseRangeByScore(String key, double min, double max, long offset, long count) {
        return this.redisTemplate.opsForZSet().reverseRangeByScore(key, min, max, offset, count);
    }

    public Set<TypedTuple<Object>> reverseRangeByScoreWithScores(String key, double min, double max, long offset, long count) {
        return this.redisTemplate.opsForZSet().reverseRangeByScoreWithScores(key, min, max, offset, count);
    }

    public long countZSet(String key, double min, double max) {
        return this.redisTemplate.opsForZSet().count(key, min, max);
    }

    public long sizeZset(String key) {
        return this.redisTemplate.opsForZSet().size(key);
    }

    public Double score(String key, Object value) {
        return this.redisTemplate.opsForZSet().score(key, value);
    }

    public Long removeRange(String key, long start, long end) {
        return this.redisTemplate.opsForZSet().removeRange(key, start, end);
    }

    public Long removeRangeByScore(String key, double min, double max) {
        return this.redisTemplate.opsForZSet().removeRangeByScore(key, min, max);
    }

    public Long unionAndStoreZset(String key, String otherKey, String destKey) {
        return this.redisTemplate.opsForZSet().unionAndStore(key, otherKey, destKey);
    }

    public Long unionAndStoreZset(String key, Collection<String> otherKeys, String destKey) {
        return this.redisTemplate.opsForZSet().unionAndStore(key, otherKeys, destKey);
    }

    public Long intersectAndStore(String key, String otherKey, String destKey) {
        return this.redisTemplate.opsForZSet().intersectAndStore(key, otherKey, destKey);
    }

    public Long intersectAndStore(String key, Collection<String> otherKeys, String destKey) {
        return this.redisTemplate.opsForZSet().intersectAndStore(key, otherKeys, destKey);
    }
}
