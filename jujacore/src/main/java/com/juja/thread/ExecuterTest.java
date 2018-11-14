package com.juja.thread;

import org.junit.Test;

import java.util.concurrent.*;

public class ExecuterTest {

    @Test
    public void test() {
        ThreadPoolExecutor executor =
                new ThreadPoolExecutor(2, 7, 11,
                        TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(512));
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.submit(() -> System.out.println("I am runnable"));
        Future<Boolean> future = executor.submit((Callable<Boolean>) () -> {
            String str = "s";
            if ("s".equals(str)) {
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        });
        Boolean result = null;
        try {
            result = future.get(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        if (result) {
            System.out.println("Ok");
        }

    }
}
