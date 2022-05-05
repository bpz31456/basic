package cn.ms22.model.decorator;

public class FileInputStreamFetch extends InputStreamFetch {
    private String fileName;

    public FileInputStreamFetch(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void fetch() {
        System.out.println("从文件"+fileName+"中抽取数据");
    }
}
