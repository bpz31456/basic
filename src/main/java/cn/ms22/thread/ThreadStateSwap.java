package cn.ms22.thread;

public class ThreadStateSwap {
    public static void main(String[] args) {
//        runningOrReady();
//        sleepToRunning();
        waitAndNotify(new Object());
    }

    /**
     * 就绪和运行两个状态切换
     * 当前线程和new出来的线程之间相互切换cpu时间片
     */
    public static void runningOrReady() {
        Thread thread = new Thread(() -> {
            while (true) System.out.println(Thread.currentThread() + "执行了。");
        });
        thread.start();
        while (true) {
            System.out.println(Thread.currentThread() + " 线程执行。");
        }
    }

    /**
     * 每次间隔sleep(time)等待时间时进入就绪状态，之后竞争cpu时间片进入Running
     */
    public static void sleepToRunning() {
        Thread thread = new Thread(() -> {
            while (true) {
                System.out.println(Thread.currentThread() + "执行了。");
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        while (true) {
            System.out.println(Thread.currentThread() + " 线程执行。");
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * thread a synchronized o,o在等待其他线程来唤醒
     * thread b 唤醒o，程序继续执行
     * @param o
     */
    public static void waitAndNotify(Object o) {
        Thread threadA = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (o) {
                    try {
                        System.out.println("o is waiting in thread a.");
                        o.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("ThreadA Runing.");
                }
            }
        });
        threadA.start();

        Thread threadB = new Thread(() -> {
            try {
                System.out.println("ThreadB is sleeping.");
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (o) {
                System.out.println("Thread b notify o.");
                o.notify();
            }
            System.out.println("ThreadB Runing.");
        });
        threadB.start();
    }


}
