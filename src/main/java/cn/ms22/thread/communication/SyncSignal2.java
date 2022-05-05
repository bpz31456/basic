package cn.ms22.thread.communication;

import java.util.concurrent.TimeUnit;

public class SyncSignal2 {
    public void set(Signal s) {
        s.setSignal();
    }

    public void get(Signal s) {
        s.getSignal();
    }

    public static void main(String[] args) {
        Signal signal = new Signal();
        SyncSignal2 syncSignal2 = new SyncSignal2();
        Thread t1 = new Thread(() -> syncSignal2.get(signal));
        Thread t3 = new Thread(() -> syncSignal2.get(signal));
        Thread t2 = new Thread(() -> syncSignal2.set(signal));

        t3.start();
        try {
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static class Signal {
        private volatile int s;

        public synchronized void setSignal() {
            System.out.println(Thread.currentThread().getName() + "开始设置值。");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.s = 1;
            notifyAll();
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "值设置完毕。");
        }

        public synchronized void getSignal() {
            System.out.println(Thread.currentThread().getName() + "开始获取值。");
            if (s != 1) {
                try {
                    wait();
                    System.out.println(Thread.currentThread().getName() + "结束wait。");
                    TimeUnit.SECONDS.sleep(3);
                    System.out.println(Thread.currentThread().getName() + "获取值成功。");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
