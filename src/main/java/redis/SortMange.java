package redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.SortingParams;

public class SortMange {
    private static Jedis jedis = new Jedis();

    public static void main(String[] args) {
        sort();
    }

    private static void sort() {
        String setEle[] = new String[]{"10", "20", "5", "9", "12", "34"};
        String zsetEle[] = new String[]{"a","b","c","d","e","f","g","h","i"};
        String listEles[] = new String[]{"zhangsan","lisi","wangwu","zhaoliu","zhouxun","liujie","tangjiy","liuhaor"};

        jedis.del("ideal:sort:set:posts");
        jedis.srem("ideal:sort:set:posts", "mi", "tang");
        for (String ele : setEle) {
            if (!jedis.sismember("ideal:sort:set:posts", ele)) {
                jedis.sadd("ideal:sort:set:posts", ele);
            }
        }
//        jedis.sadd("ideal:sort:set:posts",eles);
        System.out.println("ideal:sort:set:posts:members");
        jedis.smembers("ideal:sort:set:posts").forEach((o) -> System.out.print(o + " "));
        System.out.println("\n排序处理后");
        jedis.sort("ideal:sort:set:posts").forEach((o) -> System.out.print(o + " "));
        jedis.sort("ideal:sort:set:posts", createSortingParams(), "ideal:sort:set:posts:sorted:desc");
        System.out.println(jedis.type("ideal:sort:set:posts:sorted:desc"));
        //zset
        for (String s : zsetEle) {
            jedis.zadd("ideal:sort:zset:1",Math.random(),s);
        }
        jedis.zrangeWithScores("ideal:sort:zset:1",0,-1).forEach((o)->{
            System.out.println(o.getScore()+" "+o.getElement());
        });
        System.out.println("zset排序后：");
        jedis.sort("ideal:sort:zset:1",new SortingParams().alpha().desc()).forEach((o) -> System.out.print(o + " "));
        //list
        for (String listEle : listEles) {
            jedis.lpush("ideal:sort:list:1",listEle);
        }
        System.out.println("list 的值：");
        jedis.lrange("ideal:sort:list:1",0,-1).forEach((o) -> System.out.print(o + " "));
        System.out.println("list 排序后：");
        jedis.sort("ideal:sort:list:1",new SortingParams().alpha().desc()).forEach((o) -> System.out.print(o + " "));

        jedis.sort("docker:list:4",new SortingParams().by("mvp*").get("tang*")).forEach(System.out::print);
    }

    private static SortingParams createSortingParams() {
        SortingParams sortingParams = new SortingParams();
        sortingParams.desc().limit(1,3);
        return sortingParams;
    }

}
