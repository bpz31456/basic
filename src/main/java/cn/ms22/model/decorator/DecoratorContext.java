package cn.ms22.model.decorator;

/**
 * 模拟装饰器模式，增强相近功能
 * 定义可相互装配的两个类StageInputStreamFetch、MultithreadingInputStreamFetch
 * 给抽取速度提速
 */
public class DecoratorContext {
    public static void main(String[] args) {
        InputStreamFetch inputStreamFetch =
                new FileInputStreamFetch("C:\\dd\\cc\\a.txt");
        StageInputStreamFetch stageInputStreamFetch =
                new StageInputStreamFetch(inputStreamFetch);
//        stageInputStreamFetch.fetch();
        MultithreadingInputStreamFetch multithreadingInputStreamFetch =
                new MultithreadingInputStreamFetch(stageInputStreamFetch);
        multithreadingInputStreamFetch.fetch();
    }
}
