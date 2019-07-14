package group.zealot.test.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RunnableFuture;

public class MyThreadPoolManager {
    
    private static final MyThreadPoolManager instance = new MyThreadPoolManager();
    
    private MyThreadPool threadPool;
    
    private MyThreadPoolManager() {
        this.threadPool = new MyThreadPool(1, 30, 10000);
    }
    
    public static MyThreadPoolManager getInstance() {
        return instance;
    }
    
    public void execute(Runnable r) throws RejectedExecutionException {
        this.threadPool.execute(r);
    }
    
    public <T> Future<T> submit(Callable<T> task) {
        if (task == null)
            throw new NullPointerException();
        RunnableFuture<T> ftask = newTaskFor(task);
        execute(ftask);
        return ftask;
    }
    
    private <T> RunnableFuture<T> newTaskFor(Callable<T> callable) {
        return new FutureTask<>(callable);
    }
    
}
