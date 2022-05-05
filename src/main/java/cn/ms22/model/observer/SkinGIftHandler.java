package cn.ms22.model.observer;

public class SkinGIftHandler implements Handler {
    @Override
    public void hand() {
        System.out.println("赠送皮肤碎片。");
    }

    @Override
    public String getName() {
        return "皮肤碎片赠送";
    }
}
