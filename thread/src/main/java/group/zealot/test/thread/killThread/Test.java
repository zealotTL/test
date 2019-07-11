package group.zealot.test.thread.killThread;

import group.zealot.test.thread.Main;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Main.class)
public class Test {
    static volatile boolean fg = false;
    static volatile int i = 0;

    @org.junit.Test
    public void test() throws InterruptedException {

        Thread thread = new Thread(() -> {
            while (true) {
                i++;
                synchronized (this) {
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        System.out.println("thread 2    " + e.getClass().getName());
                    }
                    System.out.println("over 1 thread");
                }
            }
        });
        thread.start();
        System.out.println(i);
//        new Thread(() -> {
//            synchronized (thread) {
//                try {
//                    thread.wait();
//                } catch (InterruptedException e) {
//                    System.out.println("thread 2    " + e.getClass().getName());
//                }
//                System.out.println("over 2 thread");
//            }
//        }).start();
        System.out.println(thread.getState());
        thread.interrupt();
        System.out.println(i);
        Thread.sleep(1000);
        System.out.println(i);
        System.exit(0);
    }

}
