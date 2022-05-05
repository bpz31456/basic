package cn.ms22.model.single;

public class Singleton {
    private Singleton() {
    }

    private static Singleton instance = new Singleton();
    private static Singleton instance1 = null;
    private static Singleton instance2 = null;

    //饿汉式
    public static Singleton getInstance() {
        return instance;
    }

    //懒汉式
    public static synchronized Singleton  getInstance1() {
        if(instance1==null){
            instance1 = new Singleton();
        }
        return instance1;
    }

    //双重检测
    public static synchronized Singleton  getInstance2() {
        if(instance2 ==null){
            synchronized (Singleton.class){
                if(instance2==null){
                    instance2 = new Singleton();
                }
            }
        }
        return instance2;
    }

    //静态内部类
    public static Singleton getInstance3(){
        return SingletonHelper.singleton3;
    }

    private static class SingletonHelper{
        public static final Singleton singleton3 = new Singleton();
    }


}
