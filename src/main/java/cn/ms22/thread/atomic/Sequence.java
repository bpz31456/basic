package cn.ms22.thread.atomic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程安全和非线程安全的操作
 * 原子类型
 */
public class Sequence {
    //非线程安全的徐磊生成器
    public static int i;
    public static AtomicInteger j =  new AtomicInteger(0);

    public  static int getNext() {
        return ++i;
    }

    public static int getAtomicNext(){
        return j.incrementAndGet();
    }

    public static void main(String[] args) {
//        nonThreadSafeSequence();
        atomicSequence();
    }

    public static void nonThreadSafeSequence(){
        ExecutorService executorService = Executors.newCachedThreadPool();
        int i = 0;
        while(i++<100) {
            executorService.execute(() -> System.out.println(Thread.currentThread().getName() + "获取数据:" + Sequence.getNext()));
        }
        executorService.shutdown();
    }

    public static void atomicSequence(){
        ExecutorService executorService = Executors.newCachedThreadPool();
        int i = 0;
        while(i++<100) {
            executorService.execute(() -> System.out.println(Thread.currentThread().getName() + "获取数据:" + Sequence.getAtomicNext()));
        }
        executorService.shutdown();
    }
}
