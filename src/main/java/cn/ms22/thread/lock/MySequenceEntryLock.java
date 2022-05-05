package cn.ms22.thread.lock;


public class MySequenceEntryLock {
    private int i;
    private MyReentryLock lock = new MyReentryLock();

    public void getA() {
        lock.lock();
        i++;
        System.out.println("A");
        getB();
        lock.unlock();
    }

    public void getB() {
        lock.lock();
        System.out.println("B " + Thread.currentThread().getName() + " " + i);
        getC();
        lock.unlock();
    }

    public void getC() {
        lock.lock();
        System.out.println("C " + Thread.currentThread().getName() + " " + i);
        lock.unlock();
    }

    public static void main(String[] args) {
        int i = 0;
        MySequenceEntryLock mySequenceEntryLock = new MySequenceEntryLock();
        while (i++ < 10) {
            new Thread(mySequenceEntryLock::getA).start();
        }
    }
}
