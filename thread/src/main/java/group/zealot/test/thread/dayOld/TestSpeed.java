package group.zealot.test.thread.dayOld;

import group.zealot.test.thread.MyThreadPoolManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class TestSpeed {
    public void test1() throws InterruptedException {
        int num = 1000 * 10000;
         testConcurrentLinkedQueue(num);// 55387,54652,56734
         testArrayListSynchronized(num);// 47870,49124,50311
         testHashSetSynchronized(num);// 38447,28492,39892
         testConcurrentSkipListSet(num);//57709
        testConcurrentHashMap(num);// 50469,50687
    }
    
    private void testConcurrentLinkedQueue(int num) throws InterruptedException {
        System.out.println("testConcurrentLinkedQueue");
        ConcurrentLinkedQueue<String> list = new ConcurrentLinkedQueue<String>();
        MyThreadPoolManager threadPool = MyThreadPoolManager.getInstance();
        
        CountDownLatch latch = new CountDownLatch(num);
        Long start = new Date().getTime();
        System.out.println("start:" + start);
        for (int i = 0; i < num; i++) {
            threadPool.execute(new testClass_1(latch, list, "Thread" + i));
        }
        latch.await(10, TimeUnit.MINUTES);
        Long end = new Date().getTime();
        System.out.println("end:" + end);
        System.out.println("差：" + -(start.longValue() - end.longValue()));
        System.out.println("END");
    }
    
    private void testArrayListSynchronized(int num) throws InterruptedException {
        System.out.println("testArrayListSynchronized");
        ArrayList<String> list = new ArrayList<String>();
        MyThreadPoolManager threadPool = MyThreadPoolManager.getInstance();
        CountDownLatch latch = new CountDownLatch(num);
        Long start = new Date().getTime();
        System.out.println("start:" + start);
        for (int i = 0; i < num; i++) {
            threadPool.execute(new testClass_2(latch, list, "Thread" + i));
        }
        latch.await(10, TimeUnit.MINUTES);
        Long end = new Date().getTime();
        System.out.println("end:" + end);
        System.out.println("差：" + -(start.longValue() - end.longValue()));
        System.out.println("END");
    }
    
    private void testHashSetSynchronized(int num) throws InterruptedException {
        System.out.println("testHashSetSynchronized");
        HashSet<String> set = new HashSet<String>();
        MyThreadPoolManager threadPool = MyThreadPoolManager.getInstance();
        CountDownLatch latch = new CountDownLatch(num);
        Long start = new Date().getTime();
        System.out.println("start:" + start);
        for (int i = 0; i < num; i++) {
            threadPool.execute(new testClass_3(latch, set, "Thread" + i));
        }
        latch.await(10, TimeUnit.MINUTES);
        Long end = new Date().getTime();
        System.out.println("end:" + end);
        System.out.println("差：" + -(start.longValue() - end.longValue()));
        System.out.println("END");
    }
    
    private void testConcurrentSkipListSet(int num) throws InterruptedException {
        System.out.println("testConcurrentSkipListSet");
        ConcurrentSkipListSet<String> set = new ConcurrentSkipListSet<String>();
        MyThreadPoolManager threadPool = MyThreadPoolManager.getInstance();
        CountDownLatch latch = new CountDownLatch(num);
        Long start = new Date().getTime();
        System.out.println("start:" + start);
        for (int i = 0; i < num; i++) {
            threadPool.execute(new testClass_4(latch, set, "Thread" + i));
        }
        latch.await(10, TimeUnit.MINUTES);
        Long end = new Date().getTime();
        System.out.println("end:" + end);
        System.out.println("差：" + -(start.longValue() - end.longValue()));
        System.out.println("END");
    }
    
    private void testConcurrentHashMap(int num) throws InterruptedException {
        System.out.println("testConcurrentHashMap");
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<String, String>();
        MyThreadPoolManager threadPool = MyThreadPoolManager.getInstance();
        CountDownLatch latch = new CountDownLatch(num);
        Long start = new Date().getTime();
        System.out.println("start:" + start);
        for (int i = 0; i < num; i++) {
            threadPool.execute(new testClass_5(latch, map, "Thread" + i));
        }
        latch.await(10, TimeUnit.MINUTES);
        Long end = new Date().getTime();
        System.out.println("end:" + end);
        System.out.println("差：" + -(start.longValue() - end.longValue()));
        System.out.println("END");
    }
    
    class testClass_1 implements Runnable {
        private ConcurrentLinkedQueue<String> list;
        private CountDownLatch latch;
        private String str;
        
        testClass_1(CountDownLatch latch, ConcurrentLinkedQueue<String> list, String str) {
            this.latch = latch;
            this.list = list;
            this.str = str;
        }
        
        public void run() {
            list.add(str);
            this.latch.countDown();
        }
    }
    
    class testClass_2 implements Runnable {
        private ArrayList<String> list;
        private CountDownLatch latch;
        private String str;
        
        testClass_2(CountDownLatch latch, ArrayList<String> list, String str) {
            this.latch = latch;
            this.list = list;
            this.str = str;
        }
        
        public void run() {
            synchronized (list) {
                list.add(str);
            }
            this.latch.countDown();
        }
    }
    
    class testClass_3 implements Runnable {
        private HashSet<String> set;
        private CountDownLatch latch;
        private String str;
        
        testClass_3(CountDownLatch latch, HashSet<String> set, String str) {
            this.latch = latch;
            this.set = set;
            this.str = str;
        }
        
        public void run() {
            synchronized (set) {
                set.add(str);
            }
            this.latch.countDown();
        }
    }
    
    class testClass_4 implements Runnable {
        private ConcurrentSkipListSet<String> set;
        private CountDownLatch latch;
        private String str;
        
        testClass_4(CountDownLatch latch, ConcurrentSkipListSet<String> set, String str) {
            this.latch = latch;
            this.set = set;
            this.str = str;
        }
        
        public void run() {
            set.add(str);
            this.latch.countDown();
        }
    }
    
    class testClass_5 implements Runnable {
        private ConcurrentHashMap<String, String> map;
        private CountDownLatch latch;
        private String str;
        
        testClass_5(CountDownLatch latch, ConcurrentHashMap<String, String> map, String str) {
            this.latch = latch;
            this.map = map;
            this.str = str;
        }
        
        public void run() {
            map.put(str, str);
            this.latch.countDown();
        }
    }
}
