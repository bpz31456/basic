package cn.ms22.thread.pool;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;


public class ForkJoinSum {
    public static void main(String[] args) {
        ForkJoinSum forkJoinSum = new ForkJoinSum();
        forkJoinSum.sum(1, 100);
    }

    public void sum(int min, int max) {
        ForkJoinPool forkJoinPool = new ForkJoinPool(2);
        ForkJoinTask<Integer> submit = forkJoinPool.submit(new ComputJob(min, max));
        try {
            System.out.println(submit.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * 递归任务
     */
    static class ComputJob extends RecursiveTask<Integer> {
        private int min, max;

        public ComputJob(int min, int max) {
            this.max = max;
            this.min = min;
        }
        @Override
        protected Integer compute() {
            if (max <= min) {
                return min;
            } else {
                int mid = min + ((max - min) >> 1);
                ComputJob computJob1 = new ComputJob(min, mid);
                computJob1.fork();
                ComputJob computJob2 = new ComputJob(mid + 1, max);
                computJob2.fork();
                return computJob1.join() + computJob2.join();
            }
        }
    }


}
