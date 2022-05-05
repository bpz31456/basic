package cn.ms22.model.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 动态代理
 */
public class DynamicFetchInfoProxy {
    public Object createProxy(Object proxiedObject) {
        Class<?>[] interfaces = proxiedObject.getClass().getInterfaces();
        DynamicProxyHandler dynamicFetchInfoProxy = new DynamicProxyHandler(proxiedObject);
        Object o = Proxy.newProxyInstance(proxiedObject.getClass().getClassLoader(), interfaces, dynamicFetchInfoProxy);
        return o;
    }

    private class DynamicProxyHandler implements InvocationHandler {

        private Object proxiedObject;

        public DynamicProxyHandler(Object proxiedObject) {
            this.proxiedObject = proxiedObject;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("动态代理之前");
            Object result = method.invoke(proxiedObject, args);
            System.out.println("动态代理之后。");
            return result;
        }
    }

}
