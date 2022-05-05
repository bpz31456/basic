package cn.ms22.thread.communication;

import java.util.concurrent.TimeUnit;

/**
 * 各个线程内部保存局部变量
 * @author baopz
 */
public class CountThreadLocal {
    private ThreadLocal<Integer> integerThreadLocal = new ThreadLocal<Integer>(){
        @Override
        protected Integer initialValue() {
            return 1;
        }
    };
    private int getNextState(){
        int i = integerThreadLocal.get();
        integerThreadLocal.set(++i);
        return i;
    }
    public static void main(String[] args) {
        CountThreadLocal countThreadLocal = new CountThreadLocal();
        new Thread(()-> {
            for(;;) {
                System.out.println(Thread.currentThread().getName() + "值：" + countThreadLocal.getNextState());
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(()-> {
            for(;;) {
                System.out.println(Thread.currentThread().getName() + "值：" + countThreadLocal.getNextState());
                try {
                    TimeUnit.MILLISECONDS.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
