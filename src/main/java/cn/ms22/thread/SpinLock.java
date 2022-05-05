package cn.ms22.thread;

import java.util.concurrent.TimeUnit;

/**
 * 自旋锁
 */
public class SpinLock {
    public static void main(String[] args) {
       OnThread();
       OnThread();
       OnThread();
       OnThread();
        //模拟main线程自旋
        //这里为啥是！=2呢，是因为这个Monitor Ctrl-Break线程明示ideal通过run方式运行时自带的线程。
        //通过java xxx命令运行就没有Monitor Ctrl-Break线程
        while(Thread.activeCount()!=1){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("当前线程数量:"+Thread.activeCount());

            ThreadGroup currentGroup =
                    Thread.currentThread().getThreadGroup();
            int noThreads = currentGroup.activeCount();
            Thread[] lstThreads = new Thread[noThreads];
            currentGroup.enumerate(lstThreads);
            for (int i = 0; i < noThreads; i++)
                System.out.println("线程号：" + i + " = " + lstThreads[i].getName());
        }
        System.out.println("main thread 执行..");
    }

    private static void OnThread() {
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "执行。。。");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "执行完毕。。。");
        }).start();
    }
}
