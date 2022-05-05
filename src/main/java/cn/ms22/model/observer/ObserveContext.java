package cn.ms22.model.observer;

public class ObserveContext {
    public static void main(String[] args) {
        Observe observe = new OneObserve();
        Observe observe2 = new OneObserve();
        Subject subject = new OneSubject();
        subject.register(observe);
        subject.register(observe2);

        subject.notifyObject();
    }
}
