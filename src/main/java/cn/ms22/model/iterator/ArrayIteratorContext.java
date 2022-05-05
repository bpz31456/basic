package cn.ms22.model.iterator;

import java.util.Iterator;


public class ArrayIteratorContext {
    private int[] arr = new int[]{1, 2, 3, 4, 5, 6};
    private int curIdx = 0;

    public static void main(String[] args) {
        ArrayIteratorContext arrayIteratorContext = new ArrayIteratorContext();
        Itr itr = arrayIteratorContext.new Itr();
        while (itr.hasNext()) {
            System.out.println(itr.next());
        }
    }

    /**
     * 迭代器模式
     */
    private class Itr implements Iterator<Integer> {
        @Override
        public boolean hasNext() {
            return curIdx < arr.length;
        }

        @Override
        public Integer next() {
            return arr[curIdx++];
        }
    }
}
