package cn.ms22.cglib;

import cn.ms22.util.CommonUtil;
import net.sf.cglib.proxy.*;

import static java.lang.System.out;

public class EnhancerDeclaration {
    public static void main(String[] args) {
//        Enhancer enhancer = new Enhancer();
//        enhancer.setSuperclass(Person.class);
//        enhancer.setCallback();
//
//        Person p = (Person) enhancer.create();
//        p.info();
        EnhancerDeclaration enhancerDeclaration = new EnhancerDeclaration();
//        enhancerDeclaration.loader();
//        enhancerDeclaration.methodInterceptor();
//        enhancerDeclaration.invocationHandler();
//        enhancerDeclaration.fixedValue();
//        enhancerDeclaration.filter();
        enhancerDeclaration.proxyRefDispatcher();
    }

    private void proxyRefDispatcher() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Test.class);
        enhancer.setCallback((ProxyRefDispatcher) proxy -> {
            out.println(proxy.getClass().getName());
            return new Test();
        });
        Test t = (Test) enhancer.create();
        t.print();
    }

    private void filter() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Test.class);
        Callback[] callbacks = new Callback[]{(FixedValue) () -> 2
                , (InvocationHandler) (proxy, method, args) -> {
            out.println(proxy.getClass().getName());
            return null;
        }};
        enhancer.setCallbacks(callbacks);
        enhancer.setCallbackFilter(method -> {
            out.println(method.getName());
            return 1;
        });
        Test t = (Test) enhancer.create();
        t.print();
    }

    public void fixedValue() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Test.class);
        enhancer.setCallback((FixedValue) () -> 2);
        Test t = (Test) enhancer.create();
        t.print();
    }

    public void invocationHandler() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Test.class);
        enhancer.setCallback((InvocationHandler) (proxy, method, args) -> null);
        Test t = (Test) enhancer.create();
        t.print();
    }


    public synchronized void methodInterceptor() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Test.class);
        enhancer.setCallback((MethodInterceptor) (o, method, objects, methodProxy) -> {
            Object obj = null;
            if (method.getName().equals("print")) {
                out.println("before");
                obj = methodProxy.invokeSuper(o, objects);
                out.println("end");
            }
            return obj;
        });
        Test test = (Test) enhancer.create();
        test.print();
    }

    /**
     * 就是什么也不做
     */
    public void nonLoop() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(EnhancerDeclaration.class);
        enhancer.setCallback(NoOp.INSTANCE);
        enhancer.create();
    }


    public void loader() {
        Student student = new Student();
        out.println(student.getEnglishSchedule());
        CommonUtil.stop(1000);
        out.println(student.getEnglishSchedule());
        CommonUtil.stop(1000);
        out.println(student.getMathSchedule());
        CommonUtil.stop(1000);
        out.println(student.getMathSchedule());
        CommonUtil.stop(1000);
        out.println(student.getEnglishSchedule());
        CommonUtil.stop(1000);
        out.println(student.getMathSchedule());
    }

    static class Person {
        private String name;
        private int age;
        private String sex;

        public Person() {
        }

        private Person(String name, int age, String sex) {
            this.name = name;
            this.age = age;
            this.sex = sex;
        }

        public void info() {
            out.println(toString());
        }

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", sex='" + sex + '\'' +
                    '}';
        }
    }
}

class Test {
    public void print() {
        out.println("Hello world.");
    }
}
