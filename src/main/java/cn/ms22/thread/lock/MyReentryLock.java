package cn.ms22.thread.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 自己实现的可重入锁
 */
public class MyReentryLock implements Lock {
    private int lockCount = 0;
    private Thread lockBy = null;
    private boolean isLocked = false;

    /**
     * 这里如果不是同一把锁本身方法都进不来，需要等待?
     * 感觉不会，因为获取锁的过程很短，如果是同时有多个线程进入只会阻塞等待进入lock()方法
     */
    @Override
    public synchronized void lock() {
        Thread currentThread = Thread.currentThread();
        while (isLocked && !currentThread.equals(lockBy)) {//被锁住了并且不是同一把锁就需要等待，
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        isLocked = true;
        lockBy = currentThread;
        lockCount++;
    }

    @Override
    public synchronized void unlock() {
        Thread currentThread = Thread.currentThread();
        if (currentThread.equals(lockBy)) {
            isLocked = false;
            lockCount--;
            if (lockCount == 0) {
                lockBy = null;
                notify();
            }
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }


    @Override
    public Condition newCondition() {
        return null;
    }
}
