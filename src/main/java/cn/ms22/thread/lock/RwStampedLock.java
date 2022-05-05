package cn.ms22.thread.lock;

import java.util.concurrent.locks.StampedLock;

/**
 * ReentrantReadWriteLock的写饥饿问题解决办法StampedLock
 */
public class RwStampedLock {
    private StampedLock lock = new StampedLock();
    private int value;

    /**
     * 乐观锁读数据，升级乐观锁到读锁
     * upgrade from optimistic read to read lock
     */
    public void optimisticRead() {
        long stamp = lock.tryOptimisticRead();
        try {
            int v = value;
            //是否被写锁获取过，如果获取过，就要更新当前票据
            if (!lock.validate(stamp)) {
                //从新获取阻塞式锁，防止被其他线程进入写
                long lastStamp = lock.readLock();
                v = value;
                System.out.println("读取到数据" + v);
                stamp = lastStamp;
            }
        } finally {
            lock.unlock(stamp);
        }
    }

    public void read() {
        long stamp = lock.readLock();
        try {
            int i = value;
            //some op
            System.out.println("读取数据：" + i);
        } finally {
            lock.unlock(stamp);
        }

    }

    public void write() {
        long stamp = lock.writeLock();
        try {
            value++;
            System.out.println("写");
        } finally {
            lock.unlock(stamp);
        }
    }

    /**
     * 读锁更新为写锁
     */
    public void updateReadToWrite() {
        long stamp = lock.readLock();
        try {
            //读锁转换为写锁，如果返回为0则失败
            long ws = lock.tryConvertToWriteLock(stamp);
            if (ws != 0L) {
                stamp = ws;//成功就更新stamp状态
                value++;
            } else { //失败就释放读锁，获取写锁,并更新stamp为新的stamp
                lock.unlockRead(stamp);
                stamp = lock.writeLock();
            }
        } finally {
            lock.unlock(stamp);
        }
    }

    public static void main(String[] args) {

    }
}
