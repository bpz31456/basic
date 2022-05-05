package cn.ms22.model.strategy;

public class ReduceStrategy  implements FavourableStrategy{
    @Override
    public void compute() {
        System.out.println("减少固定金额");
    }
}
