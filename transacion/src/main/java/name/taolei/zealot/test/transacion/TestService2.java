package name.taolei.zealot.test.transacion;

import name.taolei.zealot.test.transacion.thread.MyThreadPoolManage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class TestService2 {
    @Autowired
    private TestService testService;

    public void test(){
        testService.test();
    }

    /**
     * 存在事物
     */
    public void testThread() {
        for (int i = 0; i < 2; i++) {
            MyThreadPoolManage.getInstance().execute(new TestThread(testService) {
                @Override
                public void run() {
                    testService.test();
                }
            });
        }
    }
}
