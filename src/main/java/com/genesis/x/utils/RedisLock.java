package com.genesis.x.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @Author liuxing
 * @Date 2020/4/28 11:20
 * @Version 1.0
 * @Description: 简单版 redis 分布式锁 实现。
 * 1、锁不可重入
 * 2、快速返回的方式：没有获取到锁，直接返回失败，不会等待重新尝试获取锁
 * 3、锁设置超时时间： 如果超时后还没有执行完成、则有可能多个线程获取到锁，则产生脏数据。注意设置锁的有效时间。
 *
 * 请参考 Redisson.getLock(key) 实现了可重入机制
 * 底层使用 script 脚本失效、map 数据结构实现
 * script 脚本 保证执行的原子性
 */
@Component
public class RedisLock {

    /*@Autowired
    private RedisTemplate redisTemplate;*/

    private static final String NX = "NX";
    private static final String EX = "EX";
    private static final String OK = "OK";
    private static final Integer OK1 = 1;
    private static final String UNLOCK_SCRIPT = "if redis.call(\"get\",KEYS[1]) == ARGV[1] then return redis.call(\"del\",KEYS[1]) else return 0 end ;";

    /**
     *
     * @param key 只在键不存在时，才对键进行设置操作。存在 则成功 不存在 则失败
     * @param value 随机token
     * @param timeout 设置键的过期时间为 second 秒
     * @return
     */
    /*public boolean lock(final String key, final String value, final long timeout){
        String execute = (String)redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection redisConnection) throws DataAccessException {
                JedisCluster jedisCluster = (JedisCluster) redisConnection.getNativeConnection();
                String result = jedisCluster.set(key, value, NX, EX, timeout);
                return result;
            }
        }, true);
        return OK.equalsIgnoreCase(execute);
    }*/

    /**
     * 解锁
     * @param key 要解锁的 key
     * @param value 要解锁的token，如果指定的token 和 存储的不一致，则解锁失败，防止任意解锁
     * @return
     */
    /*public boolean unlock(final String key, final String value){
        Boolean execute = (Boolean)redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection redisConnection) throws DataAccessException {
                JedisCluster jedisCluster = (JedisCluster) redisConnection.getNativeConnection();
                Object eval = jedisCluster.eval(UNLOCK_SCRIPT, Arrays.asList(key), Arrays.asList(value));
                return eval == null ? false : Integer.parseInt(eval.toString()) == OK1;
            }
        }, true);
        return execute;
    }*/

}
