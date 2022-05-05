package cn.ms22.thread.task;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * 生产蛋糕，异步操作
 */
public class FutureCake {
    public static void main(String[] args) {
        FutureCake f = new FutureCake();
        f.birthday();
    }

    public void birthday() {
        System.out.println("上班路上");
        //蛋糕店下单
        FutureTask<Cake> order = buy();
        //
        System.out.println("上班去了。");
        job();
        System.out.println("下班回到蛋糕店。");
        try {
            System.out.println("拿到蛋糕。" + order.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("拿着蛋糕回家");
    }

    private void job() {
        System.out.println("在单位工作");
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public FutureTask<Cake> buy() {
        System.out.println("购买蛋糕，拿到订单小票");
        FutureTask<Cake> order = new FutureTask<>(new Callable<Cake>() {
            @Override
            public Cake call() throws Exception {
                System.out.println("生产蛋糕中。");
                TimeUnit.SECONDS.sleep(5);
                System.out.println("蛋糕生产完成。");
                return new Cake("草莓蛋糕");
            }
        });
        //提交后台生产
        product(order);
        return order;
    }

    private void product(FutureTask<Cake> order) {
        new Thread(order).start();
    }

    static class Cake {
        private String name;

        public Cake(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Cake{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }
}
