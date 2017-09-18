package name.taolei.zealot.test.transacion.thread;

import java.util.concurrent.*;

public class MyThreadPoolManage {
    
    private static final MyThreadPoolManage instance = new MyThreadPoolManage();
    
    private MyThreadPool threadPool;
    
    private MyThreadPoolManage() {
        this.threadPool = new MyThreadPool(250, 250, 30, 1024 * 10000);
    }
    
    public static MyThreadPoolManage getInstance() {
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
        return new FutureTask<T>(callable);
    }
    
}
