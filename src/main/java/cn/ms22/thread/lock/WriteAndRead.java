package cn.ms22.thread.lock;

import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 写锁具有可重入（同一个线程），排他（其他线程）
 * 读锁具有可重入（同一个线程），可共享（其他线程）
 * 锁降级，将写锁降级为读锁
 * 在同一个线程获取写锁的
 * 总结：
 * 1.同一个线程中，写锁：先获取写锁，再获取当前写锁、当前线程的读锁是允许的，可重入
 * 2.同一个线程中，读锁：先获取读锁，在获取当前线程的读锁是运行的，但不允许获取当前线程的写锁，无法做到锁升级
 * 3.不同线程中，写锁，先获取写锁，不能获取其他线程的读锁和写锁
 * 4.同一个线程中，读锁，先获取读锁，再获取其他线程的读锁是允许的，但是无法获取其他线程的写锁。
 */
public class WriteAndRead {

    ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    Lock r = rwl.readLock();
    Lock w = rwl.writeLock();

    HashMap<String, Integer> hashMap = new HashMap<>();

    //方便先读后写的情况
    {
        hashMap.put("key-1", -1);
    }

    /**
     * 在释放写锁之前把读锁加入
     * 写后再读
     *
     * @param k
     * @param v
     * @return
     */
    public int writeAndRead(String k, int v) {
        w.lock();
        hashMap.put(k, v);
        r.lock();
        try {
            return hashMap.get(k);
        } finally {
            w.unlock();
            r.unlock();
        }
    }

    /**
     * 先读再写
     * @param k
     * @param v
     */
    public void readAndWrite(String k, int v) {
        r.lock();
        System.out.println(hashMap.get(k));
        w.lock();
        r.unlock();
        hashMap.put(k, v);
        w.unlock();

    }

    public static void main(String[] args) {
           new WriteAndRead().upper();
    }

    /**
     * 锁升级，会阻塞
     */
    public void upper(){
        WriteAndRead writeAndRead = new WriteAndRead();
        Thread t1 = new Thread(()-> writeAndRead.readAndWrite("key-1",1));
        t1.start();
        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    /**
     * 锁降级
     */
    public void low(){
        WriteAndRead writeAndRead = new WriteAndRead();

        int i = 0;
        while (i++ < 100) {
            int finalI = i;
            new Thread(() -> {
                System.out.println(" " + writeAndRead.writeAndRead("k" + finalI, finalI));
            }).start();
        }
    }
}
