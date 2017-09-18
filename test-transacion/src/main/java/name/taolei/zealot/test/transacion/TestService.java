package name.taolei.zealot.test.transacion;

import name.taolei.zealot.test.transacion.thread.MyThreadPoolManage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestService {
    @Autowired
    private TestDao testDao;
    @Autowired
    private TestService2 testService2;

    public void test() {
        testDao.saveA("1");
        testDao.saveA("2");
    }

    public void testThread() {
        for (int i = 0; i < 2; i++) {
            MyThreadPoolManage.getInstance().execute(new TestThread(this) {
                @Override
                public void run() {
                    testService.test();
                }
            });
        }
    }

    public void testThread(TestService testService) {
        for (int i = 0; i < 2; i++) {
            MyThreadPoolManage.getInstance().execute(new TestThread(testService) {
                @Override
                public void run() {
                    testService.test();
                }
            });
        }
    }

    public void testThread2() {
        for (int i = 0; i < 2; i++) {
            MyThreadPoolManage.getInstance().execute(new TestThread(testService2) {
                @Override
                public void run() {
                    testService2.test();
                }
            });
        }
    }

}
