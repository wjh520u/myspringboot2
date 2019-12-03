package concurrent.executor;

import java.util.Queue;
import java.util.concurrent.*;

public class Test {

    public static void main(String[] args) {

        BlockingQueue queue = new SynchronousQueue();
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 4,
                5, TimeUnit.SECONDS, queue,
                new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        System.out.println(r);
                        System.out.println("newThread"+Thread.currentThread().getName());
                        return new Thread(r);
                    }
                },
                new RejectedExecutionHandler() {
                    @Override
                    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                        System.out.println("RejectedExecutionHandler"+Thread.currentThread().getName());
                    }
                });

        for (int i = 0; i < 10; i++) {
            threadPoolExecutor.execute(new Task());
        }

        ExecutorService ex=Executors.newCachedThreadPool();
    }

    public static class Task extends Thread{
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName());
        }
    }

}
