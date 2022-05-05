package cn.ms22.model.bridge;

/**
 * 接口定义好，具体的处理逻辑有具体的实现分开发展处理
 */
public class BridgeContext {
    public static void main(String[] args) {
        INotify notify = new NormalINotify(new EmailAlertHandler());
        notify.notifyMsg();
    }
}
