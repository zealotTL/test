package name.taolei.zealot.test.transacion.thread;

import java.util.concurrent.*;


public class MyThreadPool extends ThreadPoolExecutor {

	public MyThreadPool(
			int corePoolSize, 
			int maximumPoolSize,
			int poolSelfMaintenTime,
			int blockingQueueSize
			){
		super(
				corePoolSize,
				maximumPoolSize,
				poolSelfMaintenTime,
				TimeUnit.SECONDS,
				new ArrayBlockingQueue<Runnable>(blockingQueueSize),
				new MyThreadFactory(),
				new AbortPolicy());
	}
	
	public MyThreadPool(
			int corePoolSize,  //核心线程大小
			int maximumPoolSize,  //最大线程大小
			long keepAliveTime,  // 线程没有任务，最大保持时间【调用allowCoreThreadTimeOut(boolean)可将没有任务核心线程释放】
			TimeUnit unit,//参数keepAliveTime的时间单位   分7种
			BlockingQueue<Runnable> workQueue, //一个阻塞队列
			ThreadFactory threadFactory, //线程工厂，主要用来创建线程
			RejectedExecutionHandler handler) {//表示当拒绝处理任务时的策略 4种选择
		
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
	}

}
