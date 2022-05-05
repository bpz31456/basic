package redis;

import redis.clients.jedis.Jedis;

import java.util.List;

public class StringSetGet {
    private static Jedis jedis = new Jedis();

    public static void main(String[] args) {
        stringSetGet();
        appendString();
        incr();
        decr();
        floatIncr();
        floatDecr();
        strlen();

    }

    private static void strlen() {
        jedis.set("ideal:strlen:v1","v1");
        Long strlen = jedis.strlen("ideal:strlen:v1");
        strlen = jedis.strlen("ideal:strlen:v2");
        System.out.println(strlen);
    }

    private static void appendString() {
        jedis.set("ideal:append:string:v1","v1");
        jedis.append("ideal:append:string:v1","v2");
        System.out.println(jedis.get("ideal:append:string:v1"));
        jedis.append("ideal:append:string:v2","v2");
        System.out.println(jedis.get("ideal:append:string:v2"));
    }

    private static void floatDecr() {

    }

    private static void floatIncr() {
        Double aDouble = jedis.incrByFloat("ideal:incrByFloat:v1", 0.1);
        System.out.println(aDouble);
    }

    private static void decr() {
        Long decr_v1 = jedis.decr("ideal:decr:v1");
        System.out.println(decr_v1);
    }

    private static void incr() {
        jedis.set("ideal_v2", "1");
        jedis.set("v3", "hello");
        Long ideal_v2 = jedis.incr("ideal_v2");


        Long ideal_v3 = jedis.incr("ideal:v3");
        System.out.println(ideal_v3);

        //设置给key设置初始值，并返回初始值
        Long ideal_v5 = jedis.incrBy("ideal_v5", 2);
        System.out.println(ideal_v2);
        System.out.println(ideal_v5);

    }

    private static void stringSetGet() {
        jedis.set("ideal:string:v1", "v1");
        String v1 = jedis.get("ideal:string:v1");
        System.out.println(v1);
        String v2 = jedis.get("ideal:string:v2");
        System.out.println(v2);
        jedis.mset("ideal:string:v3","v3","ideal:string:v4","v4","ideal:string:v5","v5");
        List<String> mget = jedis.mget("ideal:string:v2", "ideal:string:v3", "ideal:string:v4", "ideal:string:v5");
        mget.stream().forEach(System.out::println);
    }


}
