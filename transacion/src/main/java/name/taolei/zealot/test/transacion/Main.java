package name.taolei.zealot.test.transacion;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 */
public class Main {

    private static Logger log = LogManager.getLogger(Main.class);

    public static void main(String[] args) throws InterruptedException {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        TestService testService = (TestService) context.getBean(TestService.class);
        TestService2 testService2 = (TestService2) context.getBean(TestService2.class);
        testService.test();//有事物
        testService.testThread();//无事物
        testService2.testThread();//有事物
        testService.testThread2();//有事物
        testService.testThread(testService);//有事物
    }
}
