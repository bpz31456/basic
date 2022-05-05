package cn.ms22.model.template;

public class HtmlFetch extends FetchTemplate {
    private String url;
    private String htmlBody;

    public HtmlFetch(String url) {
        this.url = url;
    }

    @Override
    protected void fetch() {
        System.out.println("网络获取" + url + "资源");
        htmlBody = "html body";
    }

    @Override
    protected void analyse() {
        System.out.println("分析html信息" + htmlBody);
    }
}
