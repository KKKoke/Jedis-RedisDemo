package com.kkkoke.redisDemo;

import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Set;

public class JedisDemo1 {
    public static void main(String[] args) {
        // 创建jedis对象
        Jedis jedis = new Jedis("127.0.0.1", 6379);

        // 测试
        String value = jedis.ping();

        System.out.println(value);
    }

    // 操作key  String
    @Test
    public void demo1() {
        // 创建jedis对象
        Jedis jedis = new Jedis("127.0.0.1", 6379);

        // 添加
        jedis.set("name", "kkkoke");

        // 获取
        String name = jedis.get("name");

        System.out.println(name);

        // 设置多个key-value
        jedis.mset("k1", "v1", "k2", "v2");
        List<String> mget = jedis.mget("k1", "k2");
        System.out.println(mget);


        System.out.println("All keys in Jedis:");
        Set<String> keys = jedis.keys("*");
        for (String key : keys) {
            System.out.println(key);
        }
    }

    // 操作list
    @Test
    public void demo2() {
        // 创建jedis对象
        Jedis jedis = new Jedis("127.0.0.1", 6379);

        // 添加
        jedis.lpush("key1", "v1", "v2", "v3");

        // 获取
        List<String> values = jedis.lrange("key1", 0, -1);

        System.out.println(values);
    }

    // 操作set
    @Test
    public void demo3() {
        // 创建jedis对象
        Jedis jedis = new Jedis("127.0.0.1", 6379);

        // 添加
        jedis.sadd("names", "v1", "v2");

        // 获取
        Set<String> values = jedis.smembers("names");

        System.out.println(values);
    }

    // 操作hash
    @Test
    public void demo4() {
        // 创建jedis对象
        Jedis jedis = new Jedis("127.0.0.1", 6379);

        // 添加
        jedis.hset("users", "age", "30");

        // 获取
        String hget = jedis.hget("users", "age");

        System.out.println(hget);
    }

    // 操作zset
    @Test
    public void demo5() {
        // 创建jedis对象
        Jedis jedis = new Jedis("127.0.0.1", 6379);

        // 添加
        jedis.zadd("China", 100d, "shanghai");

        // 获取
        Set<String> zrange = jedis.zrange("China", 0, -1);

        System.out.println(zrange);
    }
}
