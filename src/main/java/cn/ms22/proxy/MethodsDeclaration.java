package cn.ms22.proxy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MethodsDeclaration {
    public static void main(String... args) {
        try {
            Method print = Printer.class.getMethod("print", String.class);
            print.invoke(new Printer(), "hello methods");
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    static class Printer {
        public void print(String info) {
            System.out.println(info);
        }
    }
}


