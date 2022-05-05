package cn.ms22.model.observer;

public interface Subject {
    void register(Observe observe);
    void notifyObject();
    void remove(Observe observe);
}
