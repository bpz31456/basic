package cn.ms22.thread;

/**
 * volatile 修饰字段表示，变量i写操作 happens-before 变量i读操作
 * volatile
 * 1.强制刷新缓存行数据（寄存器）到内存中，
 * 2.写会到内存后，会使其他处理器上改数据内存地址失效，导致其他线程读取的时候只能在内存中从新读取
 */
public class Volatile {
    //如果不加volatile，第二个线程无法结束，第二个线程中的i是这个线程的缓存数据i
    public volatile int i = 0;

    public static void main(String[] args) {
        Volatile aVolatile = new Volatile();
        new Thread(() -> {
            for (; aVolatile.i < 10; aVolatile.i++) {
                System.err.println(Thread.currentThread().getName() + "执行了" + aVolatile.i + "次。");
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(() -> {
            //一直读取的是cpu寄存器缓存数据
            while (aVolatile.i < 10) {
                /*try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
            }
            System.out.println(Thread.currentThread().getName() + "执行完毕");
        }).start();
    }
}
