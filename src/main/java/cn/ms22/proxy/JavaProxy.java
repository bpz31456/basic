package cn.ms22.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JavaProxy {
    public static void main(String[] args) {
        Hello hello = new HelloImpl();
        MyInvocationHandler handler = new MyInvocationHandler(hello);
        Hello proxyHello =
                (Hello) Proxy.newProxyInstance(HelloImpl.class.getClassLoader(),
                        HelloImpl.class.getInterfaces(),
                        handler);
        proxyHello.sayHello();
    }
    interface Hello{
        void sayHello();
    }

    static class HelloImpl implements Hello{

        @Override
        public void sayHello() {
            System.out.println("hello proxy");
        }
    }

    static class MyInvocationHandler implements InvocationHandler{
        private Object target;

        public MyInvocationHandler(Object target) {
            this.target = target;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("invocation say Hello");
            return method.invoke(target,args);
        }
    }
}
