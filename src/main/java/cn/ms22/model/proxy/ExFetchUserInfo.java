package cn.ms22.model.proxy;

/**
 * 继承原始类来实现代理
 */
public class ExFetchUserInfo extends FetchUserInfo {
    @Override
    public void fetch() {
        System.out.println("ex fetch user infos start");
        super.fetch();
        System.out.println("ex fetch user infos end");
    }
}
