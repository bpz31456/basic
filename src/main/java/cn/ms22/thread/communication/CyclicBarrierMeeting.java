package cn.ms22.thread.communication;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * 可以循环使用的屏障，到达一个屏障后继续执行
 */
public class CyclicBarrierMeeting {
    public static void main(String[] args) {
        CyclicBarrierMeeting cyclicBarrierMeeting = new CyclicBarrierMeeting();
        CyclicBarrier cyclicBarrier = new CyclicBarrier(10, cyclicBarrierMeeting::meeting);
        cyclicBarrierMeeting.conferenceRoom(cyclicBarrier);
    }

    public void conferenceRoom(CyclicBarrier cyclicBarrier) {
        int peopleCount = 20;
        int cyclicCount = 1;
        while (peopleCount-- > 0) {
            int finalCyclicCount = cyclicCount;
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "正在第" + finalCyclicCount + "次going to room。。");
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                gotoOffice(finalCyclicCount);
                //
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
                //到达后就可以发言了
                System.out.println(Thread.currentThread().getName() + "第" + finalCyclicCount + "次发言。。");
            }).start();
            if (peopleCount % 10 == 0) {
                cyclicBarrier.reset();
                cyclicCount++;
            }
        }
    }

    public void meeting() {
        System.out.println("开会。。。");
    }

    public void gotoOffice(int i) {
        System.out.println(Thread.currentThread().getName() + "第" + i + "次到达会议室");
    }
}
