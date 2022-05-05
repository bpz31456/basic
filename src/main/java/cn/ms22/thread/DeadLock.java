package cn.ms22.thread;

/**
 * 死锁jvm无法解决，只能最大限度规避
 */
public class DeadLock {
    public static void main(String[] args) {
        new DeadLock().deadLock();
    }

    /**
     * 相互的等待对方的资源释放，在锁里面再去获取另外的锁
     */
    public void deadLock() {
        DeadLock deadLock = new DeadLock();
        Object lock = new Object();
        Object lock2 = new Object();
        new Thread(() -> deadLock.printC(lock, lock2)).start();
        new Thread(() -> deadLock.printC(lock2, lock)).start();
    }

    public void printC(Object lock, Object lock2) {
        synchronized (lock) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //相互等待这里锁住的线程释放锁
            synchronized (lock2) {
                System.out.println("C");
            }
        }
    }


    /**
     * 线程1被线程1阻塞在DeadLock对象中
     */
    public void blocked() {
        DeadLock deadLock = new DeadLock();
        new Thread(deadLock::printA).start();
        new Thread(deadLock::printB).start();
    }

    public synchronized void printA() {
        System.out.println("A");
        while (true) {
        }
    }

    public synchronized void printB() {
        System.out.println("B");
        while (true) {
        }
    }

}
