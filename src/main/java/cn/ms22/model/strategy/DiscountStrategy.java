package cn.ms22.model.strategy;

public class DiscountStrategy implements FavourableStrategy {
    @Override
    public void compute() {
        System.out.println("打折");
    }
}
