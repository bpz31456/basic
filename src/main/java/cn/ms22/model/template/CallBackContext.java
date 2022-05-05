package cn.ms22.model.template;

public class CallBackContext {
    public static void main(String[] args) {
        new Execute().exe(()-> System.out.println("i'm call back."));
    }
}
