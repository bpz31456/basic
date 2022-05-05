package cn.ms22.model.decorator;

import java.util.concurrent.locks.ReentrantLock;

public class MultithreadingInputStreamFetch extends InputStreamFetch {
    private int threads;
    private InputStreamFetch inputStreamFetch;
    ReentrantLock lock = new ReentrantLock();

    public MultithreadingInputStreamFetch(InputStreamFetch inputStreamFetch) {
        this.inputStreamFetch = inputStreamFetch;
        threads = 10;
    }

    public MultithreadingInputStreamFetch(InputStreamFetch inputStreamFetch, int threads) {
        this(inputStreamFetch);
        this.threads = threads;
    }


    @Override
    public void fetch() {
        lock.lock();
        try {
            for (int i = 0; i < threads; i++) {
                int finalI = i;
                new Thread(() -> {
                    System.out.println("第" + finalI + "条线程开始抽取数据");
                    inputStreamFetch.fetch();
                }).start();
            }
        } finally {
            lock.unlock();
        }
    }
}
