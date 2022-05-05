package cn.ms22.proxy;

import java.lang.reflect.Field;

public class FieldDeclaration {
    public static void main(String[] args) {
        try {
            Student student = Student.class.newInstance();
            Class<? extends Student> aClass = student.getClass();
            Field sex = aClass.getField("sex");
            sex.set(student, "nv");
            Field name = aClass.getDeclaredField("name");
            //这里name是private 修饰符，所以需要设置修改权限，java8可以
            name.setAccessible(true);
            name.set(student, "zhangsan");
            Field age = aClass.getField("age");
            age.set(student, 19);
            age.setInt(student, 19);
            System.out.println(student);
        } catch (InstantiationException | IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}

class Student {
    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", age=" + age +
                '}';
    }

    private String name;
    public String sex;
    public int age;
}
