package name.taolei.zealot.test.druid;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import name.taolei.zealot.test.druid.dao1.TestDao1;
import name.taolei.zealot.test.druid.dao2.TestDao2;

/**
 * Hello world!
 *
 */
public class Main2 {
    
    private static Logger log = LogManager.getLogger(Main2.class);
    
    public static void main(String[] args) throws InterruptedException {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext2.xml");
        TestDao1 testDao1 = (TestDao1) context.getBean("testDao1");
        TestDao2 testDao2 = (TestDao2) context.getBean("testDao2");
        testDao1.saveA("123");
        testDao2.saveA("234");
    }
}
