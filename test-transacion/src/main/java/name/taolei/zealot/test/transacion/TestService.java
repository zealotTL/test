package name.taolei.zealot.test.transacion;

import name.taolei.zealot.test.transacion.thread.MyThreadPoolManage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestService {
    @Autowired
    private TestDao testDao;

    /**
     * 主线程单独调用有事物控制
     */
    public void test() {
        testDao.saveA("1");
        testDao.saveA("2");
    }

    /**
     * 子线程中使用Service中的方法，没有事物控制
     */
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

}
