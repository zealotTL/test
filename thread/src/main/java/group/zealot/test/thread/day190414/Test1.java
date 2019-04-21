package group.zealot.test.thread.day190414;

import group.zealot.test.thread.Main;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 根据https://blog.csdn.net/taoleialskdjfhg/article/details/89293519
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Main.class)
public class Test1 {
    static volatile boolean fg = false;
    static int i = 0;

    @Before
    public void Before() {
        System.out.println(" Before ");
    }

    @Test
    public void test() {
        System.out.println(" test ");
    }

    @Test
    public void volatileTest() throws InterruptedException {
        /**
         * 测试volatile 写操作先于读操作【时间上】
         * 运用变量fg、i
         */
        new Thread(() -> {
            int i = 0;
            while (i < 50) {
                if (Test1.fg) {//与下面Thread相反
                    Test1.i++;
                    Test1.fg = !Test1.fg;
                    i++;
                }
            }
        }).start();
        new Thread(() -> {
            int i = 0;
            while (i < 50) {
                if (!Test1.fg) {//与上面Thread相反
                    Test1.i++;
                    Test1.fg = !Test1.fg;
                    i++;
                }
            }
        }).start();
        Thread.sleep(1000);//保证两个子线程运行完毕
        System.out.println(i);
    }

    @After
    public void After() {
        System.out.println(" After ");
    }
}
