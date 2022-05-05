package cn.ms22.model.proxy;

/**
 * 与被代理类实现相同的接口
 */
public class FetchUserInfoProxy implements lFetch {
    private FetchUserInfo userFetchInfo;
    public FetchUserInfoProxy(FetchUserInfo fetchUserInfo){
        this.userFetchInfo = fetchUserInfo;
    }
    @Override
    public void fetch() {
        System.out.println("fetch proxy start");
        userFetchInfo.fetch();
        System.out.println("fetch proxy end");
    }
}
