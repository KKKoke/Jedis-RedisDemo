package com.kkkoke.redisDemo;

import redis.clients.jedis.Jedis;

import java.util.Objects;
import java.util.Random;

public class PhoneCode {
    public static void main(String[] args) {
        // 模拟验证码发送
        verifyCode("17340584332");

        // 验证
        getRedisCode("17340584332", "123456");
    }

    // 1.生成6位数字验证码
    public static String getCode() {
        Random random = new Random();
        String code = "";
        for (int i = 0; i < 6; i++) {
            int rand = random.nextInt(10);
            code += rand;
        }

        return code;
    }

    // 2.每个手机每天只能发送三次，验证码放到redis中，设置过期时间
    public static void verifyCode(String phone) {
        // 连接redis
        Jedis jedis = new Jedis("127.0.0.1",6379);

        // 拼接key
        // 手机发送次数key
        String countKey = "verifyCode" + phone + ":count";
        // 验证码key
        String codeKey = "verifyCode" + phone + ":code";

        // 每个手机每天只能发送三次
        String count = jedis.get(countKey);
        if (count == null) {
            // 没有发送次数，第一次发送
            // 设置发送次数是1
            jedis.setex(countKey, 24*60*60, "1");
        } else if (Integer.parseInt(count) <= 2) {
            // 发送次数+1
            jedis.incr(countKey);
        } else if (Integer.parseInt(count) > 2) {
            // 发送三次，不能再发送
            System.out.println("今天发送次数已经超过三次");
            jedis.close();
        }

        // 发送验证码放到redis里
        String vcode = getCode();
        System.out.println(vcode);
        jedis.setex(codeKey, 120, vcode);
        jedis.close();
    }

    // 3.验证码校验
    public static void getRedisCode(String phone, String code) {
        // 从redis中获取验证码
        // 连接redis
        Jedis jedis = new Jedis("127.0.0.1",6379);
        // 验证码key
        String codeKey = "verifyCode" + phone + ":code";

        String redisCode = jedis.get(codeKey);
        if (Objects.equals(redisCode, null)) {
            System.out.println("验证码已经失效");
            jedis.close();
            return;
        }
        // 判断
        if (redisCode.equals(code)) {
            System.out.println("成功");
        } else {
            System.out.println("失败");
        }
        jedis.close();
    }
}
