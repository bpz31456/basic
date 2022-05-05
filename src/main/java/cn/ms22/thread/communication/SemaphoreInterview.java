package cn.ms22.thread.communication;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Semaphore限制流量，限制资源倾斜
 * 模拟很多记者采访，同时最多只能放3个进行采访
 */
public class SemaphoreInterview {

    public void view(Semaphore semaphore){
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName()+"接受采访。");
        try {
            Random random = new Random();
            Thread.sleep(random.nextInt(2000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName()+"采访完成。");
        semaphore.release();
    }

    public static void main(String[] args) {
        SemaphoreInterview semaphoreInterview = new SemaphoreInterview();
        Semaphore semaphore = new Semaphore(3);
        int count = 22;
        while(count-->0){
            new Thread(()-> semaphoreInterview.view(semaphore)).start();
        }
    }
}
