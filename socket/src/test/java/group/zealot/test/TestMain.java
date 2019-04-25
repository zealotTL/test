package group.zealot.test;

import group.zealot.test.socket.client.HTTPClient;
import group.zealot.test.socket.Main;
import group.zealot.test.socket.listen.HTTPService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Main.class})
public class TestMain {
    @Autowired
    ApplicationContext applicationContext;

    @Test
    public void start() throws InterruptedException {
        HTTPService httpService = applicationContext.getBean(HTTPService.class);
        HTTPClient httpClient = applicationContext.getBean(HTTPClient.class);
        new Thread(() -> {
            httpService.startHTTPService(8080);
        }).start();
        Thread.sleep(1000);
        new Thread(() -> {
            httpClient.doGet("localhost", 8080, "hello.html");
        }).start();
        Thread.sleep(5000);
    }
}
