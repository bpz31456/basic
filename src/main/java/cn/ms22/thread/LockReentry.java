package cn.ms22.thread;

/**
 * Synchronized，Lock对同一个线程都可以重入同一个已经锁住的对象
 */
public class LockReentry {

    public static void main(String[] args) {
        //同一个线程重入同一个对象锁两次
        new Thread(
                () -> new LockReentry().printA()
        ).start();

        //同一个lockReentry对象
        LockReentry lockReentry = new LockReentry();
        //两个线程同时进入,某个线程先进入这个对象（lockReentry）获取锁，执行完后释放锁，
        // 另一个线程在进入这个对象（lockReentry）获取锁，执行后释放锁
        new Thread(lockReentry::printC).start();
        new Thread(lockReentry::printD).start();
    }

    /**
     * 锁住当前对象this，先执行printA，再执行printB
     */
    public synchronized void printA(){
        System.out.println("a");
        printB();
    }
    public synchronized void printB(){
        System.out.println("B");
    }

    private synchronized void printC(){
        System.out.println("c");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private synchronized void printD(){
        System.out.println("d");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
