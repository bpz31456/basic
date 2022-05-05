package cn.ms22.thread;

import java.util.concurrent.TimeUnit;

public class ThreadSafe {
    static int i = 0;

    public static void main(String[] args) {
        //safe
        safe();
    }

    /**
     * synchronized ，实例方法上，内置锁就是当前对象
     *flags: ACC_PUBLIC, ACC_STATIC, ACC_SYNCHRONIZED  //
     *     Code:
     *       stack=3, locals=0, args_size=0
     *          0: getstatic     #3                  // Field i:I
     *          3: dup
     *          4: iconst_1
     *          5: iadd
     *          6: putstatic     #3                  // Field i:I
     *          9: ireturn
     * @return
     */
    public synchronized static int getNext() {
        return i++;
    }
    /**
     * stack=2, locals=2, args_size=0
     *          0: getstatic     #3                  // Field i:I
     *          3: invokestatic  #4                  // Method java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
     *          6: dup
     *          7: astore_0
     *          8: monitorenter     //xx
     *          9: getstatic     #3                  // Field i:I
     *         12: iconst_1
     *         13: isub
     *         14: putstatic     #3                  // Field i:I
     *         17: getstatic     #3                  // Field i:I
     *         20: aload_0
     *         21: monitorexit  //xx
     *         22: ireturn
     *         23: astore_1
     *         24: aload_0
     *         25: monitorexit
     *         26: aload_1
     *         27: athrow
     * @return
     */
    public static int getPre() {
        //monitorenter 字节码
        synchronized (Integer.valueOf(i)) {
            i--;
            return i;
        }
        //monitorexit 字节码
    }

    public static void safe() {
        new Thread(() -> {
            while (true) {
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " " + ThreadSafe.getNext());
            }
        }
        ).start();
        new Thread(() -> {
            while (true) {
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " " + ThreadSafe.getNext());
            }
        }
        ).start();
    }
}
