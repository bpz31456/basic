package redis;

import redis.clients.jedis.Jedis;

public class ExpireManage {
    private static Jedis jedis = new Jedis();

    public static void main(String[] args) {
        expireTtl();
    }

    private static void expireTtl() {
        if(!jedis.exists("ideal:expire:1")){
            jedis.set("ideal:expire:1","20s");
        }
        jedis.expire("ideal:expire:1",5L);
        System.out.println(jedis.ttl("ideal:expire:1"));
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(jedis.ttl("ideal:expire:1"));
        System.out.println(jedis.get("ideal:expire:1"));
        System.out.println(jedis.ttl("ideal:expire:1"));
    }
}
