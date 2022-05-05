package cn.ms22.thread.lock;

import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class UseReadWriteLock {
    private HashMap<String, Integer> data = new HashMap<>();
    private ReadWriteLock rwl = new ReentrantReadWriteLock();
    private Lock r = rwl.readLock();
    private Lock w = rwl.writeLock();

    public static void main(String[] args) {
        UseReadWriteLock useReadWriteLock = new UseReadWriteLock();
        Thread thread1 = new Thread(useReadWriteLock::writeJob);
        thread1.start();
        try {
            thread1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Thread thread2 = new Thread(useReadWriteLock::readJob);
        thread2.start();
        /*Thread thread3 = new Thread(useReadWriteLock::writeAndReadJob);
        thread3.start();*/
        try {
//            thread1.join();
            thread2.join();
//            thread3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    /**
     * 多个线程同时读取job
     */
    private void readJob() {
        new Thread(() -> System.out.println(read("key1"))).start();
        new Thread(() -> System.out.println(read("key2"))).start();
        new Thread(() -> System.out.println(read("key3"))).start();
        new Thread(() -> System.out.println(read("key4"))).start();
    }

    private void writeAndReadJob() {
        new Thread(() -> write("key1", 1)).start();
        new Thread(() -> System.out.println(read("key1"))).start();
        new Thread(() -> write("key2", 2)).start();
        new Thread(() -> System.out.println(read("key2"))).start();
        new Thread(() -> write("key3", 3)).start();
        new Thread(() -> System.out.println(read("key3"))).start();
        new Thread(() -> write("key4", 4)).start();
        new Thread(() -> System.out.println(read("key4"))).start();
        new Thread(() -> write("key5", 5)).start();
        new Thread(() -> System.out.println(read("key5"))).start();
    }

    private void writeJob() {
        new Thread(() -> {
            write("key1", 1);
        }).start();
        new Thread(() -> {
            write("key2", 2);
        }).start();
        new Thread(() -> {
            write("key3", 3);
        }).start();
        new Thread(() -> {
            write("key4", 4);
        }).start();
    }

    public void write(String key, int value) {
        r.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "开始写"+value+"。。。");
            try {
                Thread.sleep(1000);
                data.put(key, value);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "写"+value+"执行完毕。");
        } finally {
            r.unlock();
        }
    }

    public int read(String key) {
        w.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "开始读。");
            return data.get(key);
        } finally {
            w.unlock();
        }
    }
}
