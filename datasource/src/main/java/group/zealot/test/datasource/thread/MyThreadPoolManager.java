package group.zealot.test.datasource.thread;

import java.util.concurrent.*;

public class MyThreadPoolManager {

    private final MyThreadPool threadPool;

    public MyThreadPoolManager(int poolSize) {
        this.threadPool = new MyThreadPool(poolSize, 30, 10000000);
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
