package redis;

import redis.clients.jedis.Jedis;

import java.util.Set;

public class RedisUse {
    public static void main(String[] args) {
        Jedis jedis = new Jedis();
        jedis.set("test","helo");
        jedis.set("foo","bar");
        Set<String> keys = jedis.keys("*");
        keys.stream().forEach(System.out::println);
        Boolean foo = jedis.exists("foo");
        System.out.println(foo);
        Long foo1foo1 = jedis.del("foo");
        System.out.println(foo1foo1);
         foo = jedis.exists("foo");
        System.out.println(foo);
        jedis.set("v1","1");
        jedis.lpush("v2","v2","v2","v2");
        String v1 = jedis.type("v1");
        String v2 = jedis.type("v2");
        System.out.println(v1);
        System.out.println(v2);

    }
}
