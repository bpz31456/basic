package cn.ms22.thread.communication;

import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;

/**
 * 两个线程之间的数据交换
 * 然后进行之后的处理
 * 常备用在遗传学算法中，遗传算法处理组合优化、机器学习、信号处理、自适应控制和人工生命等领域
 */
public class ExchangeData {

    public void spiderA(Exchanger<String> exchanger) {
        System.out.println("A方法开始抓取");
        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("A方法获取结束");
        String rst = "12346";
        try {
            System.out.println("A方法开始比对");
            String exchange = exchanger.exchange(rst);
            System.out.println("A方法获得结果>"+exchange);
            System.out.println("A方法比对结果。"+exchange.equals(rst));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void spiderB(Exchanger<String> exchanger) {
        System.out.println("B方法开始抓取");
        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("B方法获取结束");
        String rst = "123456";
        try {
            System.out.println("B方法开始比对");
            String exchange = exchanger.exchange(rst);
            System.out.println("B获得结果>:" + exchange);
            System.out.println("B比对结果:" + exchange.equals(rst));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void spiderC(Exchanger<String> exchanger) {
        System.out.println("C方法开始抓取");
        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("C方法获取结束");
        String rst = "1234567";
        try {
            System.out.println("C方法开始比对");
            String exchange = exchanger.exchange(rst);
            System.out.println("C获得结果>:" + exchange);
            System.out.println("C比对结果:" + exchange.equals(rst));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void spiderD(Exchanger<String> exchanger) {
        System.out.println("D方法开始抓取");
        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("D方法获取结束");
        String rst = "12345672";
        try {
            System.out.println("D方法开始比对");
            String exchange = exchanger.exchange(rst);
            System.out.println("D获得结果>:" + exchange);
            System.out.println("D比对结果:" + exchange.equals(rst));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Exchanger<String> exchanger = new Exchanger<>();
        ExchangeData exchangeData = new ExchangeData();
        new Thread(()-> exchangeData.spiderA(exchanger)).start();
        new Thread(()-> exchangeData.spiderB(exchanger)).start();
        new Thread(()-> exchangeData.spiderC(exchanger)).start();
        new Thread(()-> exchangeData.spiderD(exchanger)).start();
    }
}
