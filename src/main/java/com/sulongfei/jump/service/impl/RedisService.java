package com.sulongfei.jump.service.impl;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 缓存服务
 *
 * @author siguiyang
 */
public class RedisService {

    private RedisTemplate<String, Serializable> redisTemplate;

    public RedisService(RedisTemplate<String, Serializable> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    /**
     * set  key 不过期
     *
     * @param key   redis key
     * @param value 存储redis 的值
     */
    public void set(String key, Serializable value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * set
     *
     * @param key   redis key
     * @param value 存储redis 的值
     * @param time  时间 秒
     */
    public void set(String key, Serializable value, long time) {
        redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
    }

    /**
     * get
     *
     * @param key redis key
     */
    public <T extends Serializable> T get(String key) {
        return (T) redisTemplate.opsForValue().get(key);
    }


    /**
     * del 删除
     *
     * @param key redis key
     */
    public void del(String key) {
        redisTemplate.delete(key);
    }


    public void setValueOps(String key, String value, long time) {
        redisTemplate.boundValueOps(key).set(value, time, TimeUnit.SECONDS);
    }

    public Serializable getValueOps(String key) {
        return redisTemplate.boundValueOps(key).get();
    }

    /**
     * redis hash 处理
     * 取值 map
     *
     * @param key key
     * @return map
     */
    public Map<Object, Object> getMapOps(String key) {
        return redisTemplate.boundHashOps(key).entries();
    }

    /**
     * redis hash 处理
     * 设值
     *
     * @param key   key
     * @param value value map
     */
    public void setMapOps(String key, Map value) {
        redisTemplate.boundHashOps(key).putAll(value);
    }


    /**
     * redis list 处理
     *
     * @param key  key
     * @param data 缓存数组数据
     */
    public void setListOps(String key, ArrayList<?> data) {
        redisTemplate.opsForList().leftPush(key, data);
    }

    /**
     * 从redis 中获取list数据
     *
     * @param key redis key
     */
    public List getListOps(String key) {
        return (List) redisTemplate.opsForList().rightPop(key);
    }

    /**
     * 模糊查询key
     *
     * @param key redis key
     */
    public Set<String> keys(String key) {
        return redisTemplate.keys(key + "*");
    }

    /**
     * 批量删除key
     *
     * @param keys keys 集合
     */
    public void batchDel(Set<String> keys) {
        if (!CollectionUtils.isEmpty(keys)) {
            keys.forEach(this::del);
        }
    }

    /**
     * 从hash 中查询是否存在这个值
     *
     * @param key   key
     * @param value 存在hash 中的value
     */
    public boolean contains(String key, String value) {
        return this.getMapOps(key).containsValue(value);
    }

}
