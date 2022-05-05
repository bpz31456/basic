package cn.ms22.model.observer;

public class OneObserve implements Observe {

    @Override
    public void updateMsg() {
        System.out.println("msg");
    }
}
