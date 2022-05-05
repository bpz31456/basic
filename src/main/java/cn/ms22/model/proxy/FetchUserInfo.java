package cn.ms22.model.proxy;

public class FetchUserInfo  implements lFetch{

    @Override
    public void fetch() {
        System.out.println("fetch user infos");
    }
}
