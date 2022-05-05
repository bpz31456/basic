package cn.ms22.model.proxy;

public class FetchContext {
    public static void main(String[] args) {
        //实现同样的接口
        lFetch iFetch = new FetchUserInfoProxy(new FetchUserInfo());
        iFetch.fetch();

        //继承原始类
        iFetch = new ExFetchUserInfo();
        iFetch.fetch();

        //动态代理
        iFetch = (lFetch) new DynamicFetchInfoProxy().createProxy(new FetchUserInfo());
        iFetch.fetch();
    }
}
