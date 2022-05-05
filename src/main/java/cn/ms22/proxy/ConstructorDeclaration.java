package cn.ms22.proxy;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

public class ConstructorDeclaration {
    public static void main(String[] args) {
        Constructor<?>[] declaredConstructors = Exe.class.getDeclaredConstructors();
        for (Constructor<?> declaredConstructor : declaredConstructors) {
            if(Modifier.isPrivate(declaredConstructor.getModifiers())){
                try {
                    Exe exe = (Exe) declaredConstructor.newInstance("command");
                    exe.print();
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class Exe {
        private String command;

        private Exe(String command) {
            this.command = command;
        }

        public void print(){
            System.out.println(command);
        }
    }
}
