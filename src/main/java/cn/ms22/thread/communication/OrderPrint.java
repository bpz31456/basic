package cn.ms22.thread.communication;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 利用condition 顺序打印ABC
 * condition可以通过await()让当前线程阻塞，signal()唤醒具体的线程
 */
public class OrderPrint {
    private int signal = 0;
    ReentrantLock lock = new ReentrantLock();
    Condition conditionA = lock.newCondition();
    Condition conditionB = lock.newCondition();
    Condition conditionC = lock.newCondition();

    public void printA() {
        lock.lock();
        while (signal != 0) {
            try {
                conditionA.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        signal++;
        System.out.println("A");
        conditionB.signal();
        lock.unlock();
    }

    public void printB() {
        lock.lock();
        while (signal != 1) {
            try {
                conditionB.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        signal++;
        System.out.println("B");
        conditionC.signal();
        lock.unlock();
    }

    public void printC() {
        lock.lock();
        while (signal != 2) {
            try {
                conditionC.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        signal = 0;
        System.out.println("C");
        conditionA.signal();
        lock.unlock();
    }

    public static class AJob implements Runnable {
        private OrderPrint orderPrint;

        public AJob(OrderPrint orderPrint) {
            this.orderPrint = orderPrint;
        }

        @Override
        public void run() {
            for (; ; ) {
                orderPrint.printA();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static class BJob implements Runnable {
        private OrderPrint orderPrint;

        public BJob(OrderPrint orderPrint) {
            this.orderPrint = orderPrint;
        }

        @Override
        public void run() {
            for (; ; ) {
                orderPrint.printB();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static class CJob implements Runnable {
        private OrderPrint orderPrint;

        public CJob(OrderPrint orderPrint) {
            this.orderPrint = orderPrint;
        }

        @Override
        public void run() {
            for (; ; ) {
                orderPrint.printC();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        OrderPrint orderPrint = new OrderPrint();

        AJob aJob = new AJob(orderPrint);
        BJob bJob = new BJob(orderPrint);
        CJob cJob = new CJob(orderPrint);

        new Thread(aJob).start();
        new Thread(bJob).start();
        new Thread(cJob).start();
    }
}
