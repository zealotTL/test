package name.taolei.zealot.test.tomcat.listen;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 *
 */
public class MainStartListen {
    public static void main(String[] args) {
        @SuppressWarnings("resource")
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        HTTPService httpService = (HTTPService) context.getBean("httpService");
        httpService.startHTTPService(8080);
    }
}
