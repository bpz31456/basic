package cn.ms22.thread.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 这里i添加了volatile的话，会导致lock失效，会导致i++就不是原子性操作。
 */
public class LockSequence {
    private int i;
    private Lock lock = new ReentrantLock();

    public int getNext() {
        lock.lock();
        i++;
        lock.unlock();
        return i;
    }

    public static void main(String[] args) {
        int i = 0;
        LockSequence lockSequence = new LockSequence();
        while (i++ < 100) {
            lockSequence.printSequence();
        }
    }

    public void printSequence() {
        new Thread(() -> System.out.println(Thread.currentThread().getName() + " " + getNext())).start();
    }

}
