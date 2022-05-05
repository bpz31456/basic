package cn.ms22.thread.atomic;

import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 数组原子性操作
 */
public class AtomicController {
    public static int[] arr= {1,2,3,4,5,6};
    public static AtomicIntegerArray arrAtomic = new AtomicIntegerArray(arr);
    public static AtomicReference<User> atomicUser = new AtomicReference();
    public static AtomicIntegerFieldUpdater<User> integerFieldUpdater = AtomicIntegerFieldUpdater.newUpdater(User.class,"age");
    public static void main(String[] args) {
        User user = new User().age(12).name("zs");
        int nowAge = integerFieldUpdater.addAndGet(user, 12);
        System.out.println(nowAge);

    }




    static class User{
        public String name;
        public volatile int age;

        public User age(int age){
            this.age = age;
            return this;
        }

        public User name(String name){
            this.name = name;
            return this;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }
}
