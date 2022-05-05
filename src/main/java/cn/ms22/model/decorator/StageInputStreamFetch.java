package cn.ms22.model.decorator;

public class StageInputStreamFetch extends InputStreamFetch {
    private int stage_page = 10;
    private InputStreamFetch inputStreamFetch;

    public StageInputStreamFetch(InputStreamFetch inputStreamFetch) {
        this.inputStreamFetch = inputStreamFetch;
    }

    @Override
    public void fetch() {
        for (int i = 1; i <= stage_page; i++) {
            System.out.println("第" + i + "段抽取数据");
            inputStreamFetch.fetch();
        }
    }
}
