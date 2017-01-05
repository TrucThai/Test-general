package com.biglabs.coap;


import org.eclipse.jetty.util.BlockingArrayQueue;

import java.util.concurrent.*;

/**
 * Created by lavalamp on 29/12/2016.
 */
public class TaskExcuter {
    private ThreadPoolExecutor threadPoolTaskExecutor =null;
    public static final TaskExcuter INSTANCE = new TaskExcuter();

    private TaskExcuter(){
        int corex2 = Runtime.getRuntime().availableProcessors()*2;
        BlockingArrayQueue queue = new BlockingArrayQueue(32000);
        threadPoolTaskExecutor = new ThreadPoolExecutor(
                corex2, corex2, 1, TimeUnit.SECONDS,queue);
        threadPoolTaskExecutor.setRejectedExecutionHandler(new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {
                System.out.println("Ignored");
            }
        });
    }


    public void execute(Runnable runnable){
        this.threadPoolTaskExecutor.execute(runnable);
    }

    public ThreadPoolExecutor get(){
        return threadPoolTaskExecutor;
    }


}
