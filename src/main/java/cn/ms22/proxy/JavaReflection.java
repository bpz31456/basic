package cn.ms22.proxy;

import java.io.Serializable;
import java.lang.reflect.Method;

public class JavaReflection {
    public static void main(String[] args) {
        Class<Serializable> serializableClass = Serializable.class;
        Class<Integer> integerClass = int.class;
        Class<Student> studentClass = Student.class;
        try {
            studentClass.getMethod("c");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        Method[] declaredMethods = Student.class.getDeclaredMethods();
        for (int i = 0; i < declaredMethods.length; i++) {
            declaredMethods[i].setAccessible(true);
        }




    }
    static class  Student{
        private String name;

        public Student(String name) {
            this.name = name;
        }

        public String myName(){
            System.out.println(name);
            return name;
        }
    }
}
