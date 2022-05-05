package cn.ms22.base;

public class Base {
    public static void main(String[] args) {
        func1();
    }

    private static void func1() {
        int x = 4;
        if (x > 0) {
            System.out.println("excellent");
        } else if (x > -3) {
            System.out.println("good");
        } else {
            System.out.println("normal");
        }
    }
}
