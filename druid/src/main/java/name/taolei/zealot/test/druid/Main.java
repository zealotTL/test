package name.taolei.zealot.test.druid;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import name.taolei.zealot.test.druid.dao1.TestDao1;

/**
 * Hello world!
 *
 */
public class Main {
    
    private static Logger log = LogManager.getLogger(Main.class);
    
    public static void main(String[] args) throws InterruptedException {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        TestDao1 testDao1 = (TestDao1) context.getBean("testDao1");

        new go(testDao1,MyDataSource.datasource1).start();
        new go(testDao1,MyDataSource.datasource2).start();
//        MyDataSource.setDataSourceKey(MyDataSource.datasource1);
//        testDao1.saveA("1");
//        MyDataSource.setDataSourceKey(MyDataSource.datasource2);
//        testDao1.saveA("2");
    }
}

class go extends Thread {
    private TestDao1 testDao1;
    private String datasource;
    
    go(TestDao1 testDao1,String datasource) {
        this.testDao1 = testDao1;
        this.datasource = datasource;
    }
    
    public void run() {
        // TODO Auto-generated method stub
        MyDataSource.setDataSourceKey(datasource);
        testDao1.saveA("2222");
    }
}
