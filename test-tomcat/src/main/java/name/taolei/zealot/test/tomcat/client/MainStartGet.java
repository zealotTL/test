package name.taolei.zealot.test.tomcat.client;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 *
 */
public class MainStartGet {
    public static void main(String[] args) {
        @SuppressWarnings("resource")
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        HTTPClient httpClient = (HTTPClient) context.getBean("httpClient");
        httpClient.doGet("localhost", 8080, "hello.html");
    }
}
