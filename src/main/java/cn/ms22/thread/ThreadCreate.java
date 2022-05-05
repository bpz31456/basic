package cn.ms22.thread;

import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.*;

public class ThreadCreate {
    public static void main(String[] args) {
//       extendsThread();
//        implementRunnable();
//        anonymousClasses();
//        callable();
//        timer();
//        threadPool();
//         lambda();
//        otherLambda();
    }

    public static void otherLambda() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
        double sum = list.parallelStream().mapToDouble(value -> value + 1.0).sum();
        System.out.println(sum);
    }


    /**
     * lambda表达式创建线程
     */
    public static void lambda() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);
        //单线程
        list.stream().forEach(System.out::println);
        //多线程
        list.parallelStream().forEach(System.out::println);
        int sum = list.parallelStream().mapToInt(value -> value).sum();
        list.parallelStream().forEachOrdered(System.out::println);
        System.out.println(sum);
    }

    /**
     * 线程池,提交runnable任务
     */
    private static void threadPool() {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        int k = 0;
        while (k++ < 20) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    int i = 0;
                    while (i++ < 10) {
                        System.out.println(Thread.currentThread().getName() + "Running." + i);
                        try {
                            Thread.sleep(1000L);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
        executorService.shutdown();
    }

    /**
     * 定时器
     */
    private static void timer() {
        Timer timer = new Timer("定时器", false);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("定时任务");
            }
        }, 1000L, 2000L);
    }

    /**
     * 带返回值
     */
    public static void callable() {
        FutureTask<Integer> futureTask = new FutureTask<>(new MyCallable());
        Thread thread = new Thread(futureTask);
        thread.start();
        System.out.println("当前线程先处理任务。");
        try {
            System.out.println(futureTask.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    static class MyCallable implements Callable<Integer> {

        @Override
        public Integer call() throws Exception {
            System.out.println("计算");
            TimeUnit.SECONDS.sleep(3);
            return 1;
        }
    }

    /**
     * 匿名内部类
     */
    public static void anonymousClasses() {
        //1.子类重写run方法
        new Thread() {
            @Override
            public void run() {
                while (!interrupted()) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(getName() + " A Running.");
                }
            }
        }.start();

        //2.作为参数传递实现的Runnable类
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.interrupted()) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + " B running.");
                }
            }
        }).start();

        //子类覆盖了run方法，只会执行sub
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("runnable");
            }
        }) {
            @Override
            public void run() {
                System.out.println("sub");
            }
        }.start();
    }

    public static void implementRunnable() {
        Thread thread = new Thread(new MyThreadWorker());
        thread.start();
    }

    static class MyThreadWorker implements Runnable {
        @Override
        public void run() {
            while (!Thread.interrupted()) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "running.");
            }
        }
    }

    //extends Thread类
    public static void extendsThread() {
        MyThread myThread = new MyThread("extends Thread 1");
        myThread.start();
        //主线程执行，通过while(true)
        while (true) {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("main func");
        }
    }

    static class MyThread extends Thread {
        public MyThread(String name) {
            setName(name);
        }

        @Override
        public void run() {
            while (!interrupted()) {
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(getName() + "running");
            }
        }
    }
}
