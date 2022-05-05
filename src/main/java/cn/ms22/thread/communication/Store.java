package cn.ms22.thread.communication;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Store {
    private int count = 0;
    private static final int MAX_COUNT = 10;
    ReentrantLock reentrantLock = new ReentrantLock();

    private Condition pushCondition = reentrantLock.newCondition();
    private Condition popCondition = reentrantLock.newCondition();


    public void conditionPush() {
        reentrantLock.lock();
        while (count >= MAX_COUNT) {
            System.out.println(Thread.currentThread().getName() + "库存满了，库存：" + count);
            try {
                pushCondition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        count++;
        System.out.println(Thread.currentThread().getName() + "生产完成，库存：" + count);
        popCondition.signal();
        reentrantLock.unlock();
    }

    public void conditionPop() {
        reentrantLock.lock();
        while (count <= 0) {
            System.out.println(Thread.currentThread().getName() + "消费完了没有库存，库存：" + count);
            try {
                popCondition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        count--;
        System.out.println(Thread.currentThread().getName() + "消费，现有库存：" + count);
        pushCondition.signal();
        reentrantLock.unlock();
    }


    public synchronized void push() {
        while (count >= MAX_COUNT) {
            try {
                System.out.println(Thread.currentThread().getName() + "库存满了，库存：" + count);
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        count++;
        System.out.println(Thread.currentThread().getName() + "生产完成，库存：" + count);

        notifyAll();
    }

    public synchronized void pop() {
        while (count <= 0) {
            System.out.println(Thread.currentThread().getName() + "消费完了没有库存，库存：" + count);
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        count--;
        System.out.println(Thread.currentThread().getName() + "消费，现有库存：" + count);
        notifyAll();
    }

    public static void main(String[] args) {
        Store store = new Store();
        //synchronized 信号
        ProducerJob producerJob = new ProducerJob(store);
        ConsumerJob consumerJob = new ConsumerJob(store);

        producerJob = new ConditionProducerJob(store);
        consumerJob = new ConditionConsumerJob(store);

        ExecutorService executorService = Executors.newCachedThreadPool();
        int proCount = 5;
        int popCount = 4;
        for (int i = 0; i < proCount; i++) {
            executorService.execute(producerJob);
        }

        for (int i = 0; i < popCount; i++) {
            executorService.execute(consumerJob);
        }
        executorService.shutdown();
    }


    static class ProducerJob implements Runnable {
        private Store store;
        public Store getStore(){
            return store;
        }

        public ProducerJob(Store store) {
            this.store = store;
        }

        @Override
        public void run() {
            for (; ; ) {
                store.push();
                try {
                    TimeUnit.MILLISECONDS.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class ConsumerJob implements Runnable {
        private Store store;

        public Store getStore(){
            return store;
        }

        public ConsumerJob(Store store) {
            this.store = store;
        }

        @Override
        public void run() {
            for (; ; ) {
                store.pop();
                try {
                    TimeUnit.MILLISECONDS.sleep(800);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class ConditionProducerJob extends ProducerJob{

        public ConditionProducerJob(Store store) {
            super(store);
        }

        @Override
        public void run() {
            for (; ; ) {
                getStore().conditionPush();
                try {
                    TimeUnit.MILLISECONDS.sleep(800);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class ConditionConsumerJob extends ConsumerJob {

        public ConditionConsumerJob(Store store) {
            super(store);
        }

        @Override
        public void run() {
            for (; ; ) {
                getStore().conditionPop();
                try {
                    TimeUnit.MILLISECONDS.sleep(800);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
