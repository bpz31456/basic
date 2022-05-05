package cn.ms22.thread;

/**
 * 线程活跃性问题
 */
public class ThreadAlive {
    public static void main(String[] args) {
        hunger();
        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 饥饿，线程优先级问题
     */
    public static void hunger() {
        Thread thread1 = new Thread(() -> {
            int k = 0;
            while(!Thread.interrupted() && k++<100) {
                System.out.println("优先级高的线程。"+k);
            }
        },"线程A");
        thread1.setPriority(Thread.MAX_PRIORITY);
        thread1.start();

        Thread thread2 = new Thread(() -> {
            int k = 0;
            while(!Thread.interrupted() && k++<100) {
                System.out.println("优先级低的线程。"+ k);
            }
        },"线程B");
        thread2.setPriority(Thread.MIN_PRIORITY);
        thread2.start();
    }
}
