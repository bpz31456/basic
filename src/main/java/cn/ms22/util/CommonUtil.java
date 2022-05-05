package cn.ms22.util;

import java.util.concurrent.TimeUnit;

public final class CommonUtil {
    public static void stop(long mill){
        try {
            TimeUnit.MILLISECONDS.sleep(mill);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
