package cn.ms22.thread.communication;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 等待线程执行完成后再继续后续执行
 * @author baopz
 */
public class SumWithCountDownLatch {
    private int[][] nums = new int[][]{{12, 34, 51, 23, 5, 11}, {3, 9, 8, 2, 3}, {12, 5, 23, 47, 28}, {22, 63, 12, 5, 3}};
    private int sumAll = 0;
    /**
     * 可以采用这种方法，每个线程都有一个index，每个index对应的数组段存放结果值，不会有其他的线程锁开销
     */
    private int[] sumAll2 = new int[nums.length];
    Lock lock = new ReentrantLock();

    public void sum(int num) {
        lock.lock();
        sumAll += num;
        lock.unlock();
    }

    public void ini() {
        lock.lock();
        sumAll = 0;
        lock.unlock();
    }

    public static void main(String[] args) {
        SumWithCountDownLatch sumWithCountDownLatch = new SumWithCountDownLatch();
        //线程threadActiveCount的while判断浪费cpu时间
//        sumWithCountDownLatch.threadActiveCount();
        //countDownLatch计数器执行
        sumWithCountDownLatch.countDownLatch();
    }

    public void countDownLatch() {
        CountDownLatch countDownLatch = new CountDownLatch(nums.length);
        for (int[] subNums : nums) {
            new Thread(() -> {
                int curSum = sum(subNums);
                sum(curSum);
                System.out.println(Thread.currentThread().getName() + "sum: " + curSum);
                countDownLatch.countDown();
            }).start();
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "计算结果:" + sumAll);
    }

    public void threadActiveCount() {
        for (int[] subNums : nums) {
            new Thread(() -> {
                int curSum = sum(subNums);
                sum(curSum);
                System.out.println(Thread.currentThread().getName() + "sum: " + curSum);
            }).start();
        }
        while (Thread.activeCount() > 4) {
        }
        System.out.println(Thread.currentThread().getName() + "计算结果:" + sumAll);
    }

    /**
     * 每个数组里面的数相加
     *
     * @param subNums
     * @return
     */
    public int sum(int[] subNums) {
        return Arrays.stream(subNums).sum();
    }
}
