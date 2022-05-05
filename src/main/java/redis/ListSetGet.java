package redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.ListPosition;

import java.util.List;

public class ListSetGet {
    private static Jedis jedis = new Jedis();
    public static void main(String[] args) {
//        listSetGet();
//        listLrange();
//        delListValue();
//        indexList();
//        ltrimList();
//        listInsert();
        rpoplpushList();
    }

    private static void rpoplpushList() {
        jedis.rpoplpush("ideal:list:3","ideal:list:4");
        jedis.lrange("ideal:list:5",0,-1).forEach(System.out::println);
    }

    private static void listInsert() {
        jedis.lrange("ideal:list:3",0,-1).forEach(System.out::println);
        jedis.linsert("ideal:list:3", ListPosition.AFTER,"1","22");
        System.out.println("更新之后.");
        jedis.lrange("ideal:list:3",0,-1).forEach(System.out::println);
    }

    private static void ltrimList() {
        jedis.ltrim("ideal:list:3",1,2);
        jedis.lrange("ideal:list:3",0,-1).forEach(System.out::println);
    }

    private static void indexList() {
        String lindex = jedis.lindex("ideal:list:3", 2);
        System.out.println(lindex);
        jedis.lset("ideal:list:3",2,"15");
        lindex = jedis.lindex("ideal:list:3", 2);
        System.out.println(lindex);
        jedis.lrange("ideal:list:3",0,-1).forEach(System.out::println);
    }

    private static void delListValue() {

        jedis.lrem("ideal:list:3",5,"2");
        jedis.lpop("ideal:list:3",jedis.llen("ideal:list:3").intValue()).forEach(System.out::println);
    }

    private static void listLrange() {
        jedis.lpush("ideal:list:3","1","2","3","4","5");
        List<String> lrange = jedis.lrange("ideal:list:3", 0, 2);
        lrange.forEach(System.out::println);
        jedis.lrange("ideal:list:3",-2,-1).forEach(System.out::println);
    }

    private static void listSetGet() {
        jedis.lpush("ideal:list:1","1");
        jedis.lpush("ideal:list:1","2");
        jedis.lpush("ideal:list:1","3");
        jedis.lpush("ideal:list:1","4");
        jedis.lpush("ideal:list:1","5");
        jedis.lpush("ideal:list:2","21","22","23","24","25");


        jedis.rpush("ideal:list:1","6");
        jedis.rpush("ideal:list:1","7");
        jedis.rpush("ideal:list:1","8");
        jedis.rpush("ideal:list:1","9");
        jedis.rpush("ideal:list:1","10");

        List<String> lpop1 = jedis.lpop("ideal:list:2", 10);
        lpop1.forEach(System.out::println);
        String lpop = jedis.lpop("ideal:list:1");
        System.out.println(lpop);
        String rpop = jedis.rpop("ideal:list:1");
        System.out.println(rpop);
    }
}
