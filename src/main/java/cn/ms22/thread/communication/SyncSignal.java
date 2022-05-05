package cn.ms22.thread.communication;

/**
 * 模拟线程间通信，使用共享变量signal的值做改变
 */
public class SyncSignal {

    public volatile int signal = 0;

    public static void main(String[] args) {
//        simulationSignalWithFor();
        simulationSignalWithWaitAndNotify();
    }

    private static void simulationSignalWithWaitAndNotify() {
        SyncSignal syncSignal = new SyncSignal();
        Thread thread1 = new Thread(() -> {
            synchronized (syncSignal) {
                System.out.println(Thread.currentThread().getName() + "执行。");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                syncSignal.signal = 1;
                System.out.println(Thread.currentThread().getName() + "执行完成。");
                syncSignal.notify();
            }
        });


        Thread thread2 = new Thread(() -> {
            synchronized (syncSignal) {
                System.out.println(Thread.currentThread().getName() + "等待其他线程执行完毕后再执行");
                while (syncSignal.signal != 1) {
                    try {
                        System.out.println(Thread.currentThread().getName() + "进入wait。");
                        syncSignal.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "执行完成。");
                    return;
                }
            }
        });
        thread2.start();
        thread1.start();
    }

    private static void simulationSignalWithFor() {
        SyncSignal syncSignal = new SyncSignal();
        Thread thread1 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "执行。");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            syncSignal.signal = 1;
            System.out.println(Thread.currentThread().getName() + "执行完成。");
        });
        thread1.start();

        Thread thread2 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "等待其他线程执行完毕后再执行");
            for (; ; ) {
                if (syncSignal.signal == 1) {
                    System.out.println(Thread.currentThread().getName() + "执行完成。");
                    return;
                }
            }
        });
        thread2.start();
    }
}
