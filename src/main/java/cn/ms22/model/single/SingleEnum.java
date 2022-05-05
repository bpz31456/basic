package cn.ms22.model.single;

/**
 * 单例模式，枚举类型
 */
public enum  SingleEnum {
    INSTANCE;
    public void getId(){
        System.out.println("get ID.");
    }

    public static void main(String[] args) {
       SingleEnum.INSTANCE.getId();
    }
}
