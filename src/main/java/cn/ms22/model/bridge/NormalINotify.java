package cn.ms22.model.bridge;

/**
 * 具体定义一个普通通知的类，普通通知使用邮件通知开发人员
 */
public class NormalINotify implements INotify {

    private IAlertHandler IAlertHandler;

    public NormalINotify(IAlertHandler IAlertHandler) {
        this.IAlertHandler = IAlertHandler;
    }

    @Override
    public void notifyMsg() {
        System.out.println("normal msg.");
        IAlertHandler.hand();
    }
}
