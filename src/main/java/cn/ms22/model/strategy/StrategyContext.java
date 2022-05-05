package cn.ms22.model.strategy;

public class StrategyContext {
    public static void main(String[] args) {
        StrategyContext strategyContext = new StrategyContext();
        strategyContext.compute("reduce");
        strategyContext.compute("discount");
    }

    public void compute(String type) {
        FavourableStrategy favourableStrategy = FavourableStrategyFactory.getFavourableStrategy(type);
        favourableStrategy.compute();
    }
}
