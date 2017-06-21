package name.taolei.zealot.test.thread;

import java.util.concurrent.ThreadFactory;

public class MyThreadFactory implements ThreadFactory {

	public MyThread newThread(Runnable r){
		return new MyThread(r);
	}
}
