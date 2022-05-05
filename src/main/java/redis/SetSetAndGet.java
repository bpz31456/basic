package redis;

import redis.clients.jedis.Jedis;

public class SetSetAndGet {
    private static Jedis jedis = new Jedis();
    public static void main(String[] args) {
//        setAddAndRem();
//        setMembers();
//        sismember();
//        diffInterUnion();
        other();
    }

    private static void other() {
        jedis.srandmember("ideal:set:3",2).forEach(System.out::println);
        System.out.println(jedis.scard("ideal:set:3"));
        System.out.println(jedis.spop("ideal:set:3"));
        System.out.println(jedis.scard("ideal:set:3"));

    }

    private static void diffInterUnion() {
        System.out.println("set1 diff set2:");
        jedis.sdiff("ideal:set:1","ideal:set:2","ideal:set:3").forEach(System.out::println);
        System.out.println("set2 diff set1:");
        jedis.sdiff("ideal:set:2","ideal:set:1").forEach(System.out::println);
        System.out.println("set2 inter set1:");
        jedis.sinter("ideal:set:2","ideal:set:1").forEach(System.out::println);
        System.out.println("set2 union set1:");
        jedis.sunion("ideal:set:2","ideal:set:1").forEach(System.out::println);

    }

    private static void sismember() {
        Boolean sismember = jedis.sismember("ideal:set:1", "5");
        System.out.println(sismember);
    }

    private static void setMembers() {
        jedis.smembers("ideal:set:1").forEach(System.out::println);
    }

    private static void setAddAndRem() {
        jedis.sadd("ideal:set:1","1","2","5","7","8");
        jedis.srem("ideal:set:1","2","3","8");

    }

}
