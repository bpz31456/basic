package cn.ms22.model.observer;

public class CoinGiftHandler implements Handler {
    @Override
    public void hand() {
        System.out.println("金币赠送成功");
    }

    @Override
    public String getName() {
        return "金币赠送";
    }
}
