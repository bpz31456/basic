package cn.ms22.model.observer;

public class GIftContext {
    public static void main(String[] args) {
        LoginEvent loginEvent= new LoginEvent();
        loginEvent.register(new CoinGiftHandler());
        loginEvent.register(new SkinGIftHandler());
        loginEvent.login();
    }
}
