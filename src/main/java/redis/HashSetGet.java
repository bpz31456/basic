package redis;

import redis.clients.jedis.Jedis;

import java.util.*;

public class HashSetGet {
    private static Jedis jedis = new Jedis();

    public static void main(String[] args) {
//        hashSetGet();
//        hFiledExists();
//        hkeyVals();
        articleSave();

    }

    private static void articleSave() {
        String[] names = new String[]{"c", "c++", "java", "python", "golang", "ruby", "js"};
        Random random = new Random();
        for (int i = 0; i < names.length; i++) {
            HashMap<String, String> article = new HashMap<>();
            article.put("name", names[i]);
            article.put("id", "" + i);
            article.put("create_time", random.nextInt(100) + "");
            jedis.hset("article"+i,article);
        }

    }

    private static void hkeyVals() {
        Set<String> hkeys = jedis.hkeys("ideal:user:2");
        hkeys.forEach(System.out::println);
        List<String> hvals = jedis.hvals("ideal:user:1");
        hvals.forEach(System.out::println);
    }

    private static void hFiledExists() {
        Boolean username = jedis.hexists("ideal:user:2", "username");
        Boolean name = jedis.hexists("ideal:user:2", "name");

        Boolean exists = jedis.exists("ideal:user:2");
        System.out.println(username + " " + name + " " + exists);
    }

    private static void hashSetGet() {
        jedis.del("ideal:user:2");
        HashMap<String, String> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("id", "1");
        objectObjectHashMap.put("name", "张三");
        objectObjectHashMap.put("age", "24");
        jedis.hset("ideal:user:1", objectObjectHashMap);
        Map<String, String> stringStringMap = jedis.hgetAll("ideal:user:1");
        stringStringMap.forEach((k, v) -> System.out.println(k + ":" + v));


        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("name", "李四");
        jedis.hmset("ideal:user:2", hashMap);
        List<String> hmget = jedis.hmget("ideal:user:2", "name");
        hmget.forEach(System.out::println);

        //当不存在时赋值，如果存在就不赋值，如果不存在就赋值
        jedis.hsetnx("ideal:user:2", "name", "lisi");
        String name = jedis.hget("ideal:user:2", "name");
        jedis.hsetnx("ideal:user:2", "age", "13");
        String age = jedis.hget("ideal:user:2", "age");
        System.out.println(name);
        System.out.println(age);
    }
}
