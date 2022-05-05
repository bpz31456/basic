package redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

public class TransactionManage {

    private static Jedis jedis = new Jedis();
    public static void main(String[] args) {
//        multi();
        watch();
    }

    private static void watch() {
        String watch = jedis.watch("multi:set:1");
        Transaction multi = jedis.multi();
        multi.sadd("multi:set:1","6","7","8","9");
        multi.exec();
        jedis.smembers("multi:set:1").forEach(System.out::println);
    }

    private static void multi() {
        Transaction multi = jedis.multi();
        multi.sadd("multi:set:1","1","2","3","4");
        multi.zadd("multi:set:2",10,"a");
        multi.zadd("multi:set:2",20,"b");
        multi.zadd("multi:set:2",30,"c");
        multi.exec();
        jedis.smembers("multi:set:1").forEach(System.out::println);
        jedis.zrange("multi:set:2",0 ,-1).forEach(System.out::println);
    }
}
