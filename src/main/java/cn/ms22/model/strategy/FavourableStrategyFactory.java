package cn.ms22.model.strategy;

import java.util.concurrent.ConcurrentHashMap;

public class FavourableStrategyFactory {
    private static ConcurrentHashMap<String, FavourableStrategy> map =
            new ConcurrentHashMap<>();

    static {
        map.put("reduce", new ReduceStrategy());
        map.put("discount", new DiscountStrategy());
    }

    public static FavourableStrategy getFavourableStrategy(String type) {
        return map.get(type);
    }
}
