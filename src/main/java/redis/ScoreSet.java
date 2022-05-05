package redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.Set;

public class ScoreSet {
    private static Jedis jedis = new Jedis();
    public static void main(String[] args) {
//        addValueToScoreSet();
//        getScoreFromZset();
        zincrBy();
    }

    private static void zincrBy() {
        jedis.zincrby("ideal:zset:1",4,"a");
        Double a = jedis.zscore("ideal:zset:1", "a");
        System.out.println(a);

    }

    private static void getScoreFromZset() {
        Double a = jedis.zscore("ideal:zset:1", "a");
        System.out.println(a);
    }

    private static void addValueToScoreSet() {
        jedis.zadd("ideal:zset:1",10,"a");
        jedis.zadd("ideal:zset:1",20,"b");
        jedis.zadd("ideal:zset:1",30,"c");
        jedis.zadd("ideal:zset:1",40,"d");
        jedis.zadd("ideal:zset:1",10,"b");

        jedis.zrange("ideal:zset:1",0,3).forEach(System.out::println);
        jedis.zrangeByScore("ideal:zset:1",0,20).forEach(System.out::println);
        Set<Tuple> tuples = jedis.zrangeByScoreWithScores("ideal:zset:1", 0, 50);
        tuples.forEach(o-> System.out.println("score:"+o.getScore()+" value:"+o.getElement()));
    }
}
