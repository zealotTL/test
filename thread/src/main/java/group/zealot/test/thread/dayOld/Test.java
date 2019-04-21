package group.zealot.test.thread.dayOld;

import group.zealot.test.thread.MyThreadPoolManager;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Test {
    
    /**
     * wait notifid
     */
    public void test6() {
        MyThreadPoolManager threadPool = MyThreadPoolManager.getInstance();
        Object lock = new Object();
        threadPool.execute(new testClass6_1(lock));
        threadPool.execute(new testClass6_1(lock));
        threadPool.execute(new testClass6_2(lock));
    }
    
    class testClass6_1 implements Runnable {
        private Object lock;
        
        testClass6_1(Object lock) {
            this.lock = lock;
            
        }
        
        public void run() {
            
            System.out.println("我是：" + Thread.currentThread().getName() + "，我尝试获取lock锁");
            synchronized (lock) {
                try {
                    System.out.println("我是：" + Thread.currentThread().getName() + "，我已经获取lock锁");
                    System.out
                            .println("我是：" + Thread.currentThread().getName() + "，释放lock锁，等待其他线程lock.notify来唤醒我继续执行操作");
                    lock.wait();
                    System.out.println("我是：" + Thread.currentThread().getName() + "，我已获取lock锁并且被唤醒");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    class testClass6_2 implements Runnable {
        private Object lock;
        
        testClass6_2(Object lock) {
            this.lock = lock;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        public void run() {
            System.out.println("我是：" + Thread.currentThread().getName() + "，我尝试获取lock锁");
            synchronized (lock) {
                System.out.println("我是：" + Thread.currentThread().getName() + "，我已经获取lock锁");
                System.out.println("我是：" + Thread.currentThread().getName() + "，我已经完成操作，通知其他lock.wait线程继续干活");
                lock.notify();
                lock.notify();
                // lock.notifyAll();
                System.out.println("我是：" + Thread.currentThread().getName() + "，通知已完成，但我还没释放lock锁，等我执行完，才会释放lock锁");
            }
        }
    }
    
    /**
     * 异步处理
     */
    public void test5() {
        MyThreadPoolManager threadPool = MyThreadPoolManager.getInstance();
        int i = 0;
        System.out.println("前" + i);
        Future<Integer> future = test5GetAdd(threadPool, i);
        System.out.println("后" + i);
        try {
            System.out.println("调用" + future.get(5, TimeUnit.SECONDS));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        
    }
    
    /**
     * 替换后执行方法
     */
    private Future<Integer> test5GetAdd(MyThreadPoolManager threadPool, final Integer i) {
        return threadPool.submit(new Callable<Integer>() {
            public Integer call() {
                return test5GetAdd(i);
            }
        });
    }
    
    /**
     * 原执行方法
     */
    private Integer test5GetAdd(Integer i) {
        try {
            Thread.sleep(1 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ++i;
    }
    
    /**
     * 两个线程直接进行等待，然后交换数据
     */
    public void test4() {
        Exchanger<Integer> exchanger = new Exchanger<Integer>();
        MyThreadPoolManager threadPool = MyThreadPoolManager.getInstance();
        threadPool.execute(new testClass4(exchanger, 1));
        threadPool.execute(new testClass4(exchanger, 2));
        ;
    }
    
    class testClass4 implements Runnable {
        private Integer i;
        private Exchanger<Integer> exchanger;
        
        testClass4(Exchanger<Integer> exchanger, Integer i) {
            this.i = i;
            this.exchanger = exchanger;
        }
        
        public void run() {
            int x = i;
            System.out.println("线程" + x + ",打印" + i);
            try {
                i = exchanger.exchange(i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("线程" + x + ",打印" + i);
        }
    }
    
    /**
     * 控制代码可同时执行的线程数
     */
    public void test3() {
        MyThreadPoolManager threadPool = MyThreadPoolManager.getInstance();
        Semaphore semaphore = new Semaphore(3);
        int num = 10;
        for (int i = 0; i < num; i++) {
            threadPool.execute(new testClass3(semaphore, i));
        }
    }
    
    class testClass3 implements Runnable {
        private int i;
        private Semaphore semaphore;
        
        testClass3(Semaphore semaphore, int i) {
            this.i = i;
            this.semaphore = semaphore;
        }
        
        public void run() {
            try {
                semaphore.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                System.out.println("" + this.i);
                Thread.sleep(1 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            semaphore.release();
        }
    }
    
    /**
     * 所有线程都达到条件后，一同执行后续操作
     */
    public void test2() {
        MyThreadPoolManager threadPool = MyThreadPoolManager.getInstance();
        int num = 10;
        CyclicBarrier clb = new CyclicBarrier(num + 1);
        for (int i = 0; i < num; i++) {
            threadPool.execute(new testClass2(clb, i));
        }
        try {
            clb.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
        System.out.println("一同END");
    }
    
    class testClass2 implements Runnable {
        private int i;
        private CyclicBarrier clb;
        
        testClass2(CyclicBarrier clb, int i) {
            this.clb = clb;
            this.i = i;
        }
        
        public void run() {
            System.out.println("" + this.i);
            try {
                this.clb.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println("一同" + this.i);
        }
    }
    
    /**
     * 子线程满足条件后，指定线程从阻塞中唤醒
     * 
     * @throws InterruptedException
     */
    public void test1() throws InterruptedException {
        MyThreadPoolManager threadPool = MyThreadPoolManager.getInstance();
        int num = 10;
        CountDownLatch latch = new CountDownLatch(num);
        for (int i = 0; i < num; i++) {
            threadPool.execute(new testClass1(latch, i));
        }
        latch.await(10, TimeUnit.SECONDS);
        System.out.println("---" + latch.getCount());
        System.out.println("END");
    }
    
    class testClass1 implements Runnable {
        private int i;
        private CountDownLatch latch;
        
        testClass1(CountDownLatch latch, int i) {
            this.latch = latch;
            this.i = i;
        }
        
        public void run() {
            System.out.println("" + this.i);
            System.out.println(0 / 0);
            this.latch.countDown();
        }
    }
}
