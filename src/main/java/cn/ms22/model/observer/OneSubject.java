package cn.ms22.model.observer;

import java.util.ArrayList;

public class OneSubject implements Subject {
    private ArrayList<Observe> arrayList = new ArrayList();
    @Override
    public void register(Observe observe) {
        arrayList.add(observe);
    }

    @Override
    public void notifyObject() {
        arrayList.forEach(Observe::updateMsg);
    }

    @Override
    public void remove(Observe observe) {
        arrayList.remove(observe);
    }
}
