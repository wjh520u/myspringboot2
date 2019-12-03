import java.util.*;
import java.util.concurrent.Callable;

import java.util.concurrent.ExecutionException;

import java.util.concurrent.FutureTask;


public class Test implements Callable<Integer> {

    public static void main(String[] args) {

        Test ctt = new Test();

        FutureTask<Integer> ft = new FutureTask<>(ctt);
                new Thread(ft, "有返回值的线程").start();
        try {

            System.out.println("子线程的返回值：" + ft.get());

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    @Override
    public Integer call() throws Exception {

        int i = 0;
        for (; i < 100; i++) {
            //System.out.println(Thread.currentThread().getName() + " " + i);
        }
        return i;

    }

}
