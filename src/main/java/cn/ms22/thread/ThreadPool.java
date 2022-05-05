package cn.ms22.thread;

import java.util.concurrent.*;

public class ThreadPool {
    public static void main(String[] args) {
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10,
                20,
                0,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(10),
                threadFactory,
                new ThreadPoolExecutor.DiscardPolicy());
        threadPoolExecutor.prestartAllCoreThreads();
        for (int  i = 0; i < 100; i++) {
            threadPoolExecutor.execute(() -> {
                System.out.println(Thread.currentThread().getName());
            });
        }
        threadPoolExecutor.shutdown();

    }
}
