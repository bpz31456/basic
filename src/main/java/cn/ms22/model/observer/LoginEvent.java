package cn.ms22.model.observer;

import java.util.ArrayList;

public class LoginEvent implements Event {
    private ArrayList<Handler> handlers = new ArrayList<>();
    public void login(){
        System.out.println("登录");
        executeHandlers();
    }
    @Override
    public void register(Handler handler) {
        handlers.add(handler);
        System.out.println(handler.getName()+"注册完成。");

    }

    @Override
    public void removeHandler(Handler handler) {
        handlers.remove(handler);
        System.out.println(handler.getName()+"删除注册。");
    }

    //这里的实现方式可分为同步，异步，RPC，消息队列等
    @Override
    public void executeHandlers() {
        handlers.forEach(Handler::hand);
    }
}
