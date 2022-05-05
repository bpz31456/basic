package cn.ms22.proxy;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ClassTrouble {
    public static void main(String... args) {
        try {
            Class<?> c = Class.forName("cn.ms22.proxy.Cls");
            Constructor<?>[] constructors = c.getConstructors();
            for (Constructor<?> constructor : constructors) {
                try {
                    constructor.newInstance();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
            //modifiers "private"
            c.newInstance();  // InstantiationException

            // production code should handle these exceptions more gracefully
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException x) {
            x.printStackTrace();
        }
    }

}

class Cls {
    public Cls() {
        System.out.println("hello");
    }
}
