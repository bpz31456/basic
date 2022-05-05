package cn.ms22.model.bridge;

public class EmailAlertHandler implements IAlertHandler{
    @Override
    public void hand() {
        System.out.println("send Email hand.");
    }
}
