package cn.ms22.model.observer;

public interface Event {
    void register(Handler handler);
    void removeHandler(Handler handler);
    void executeHandlers();
}
