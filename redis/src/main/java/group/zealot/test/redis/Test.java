package group.zealot.test.redis;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Async;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Main.class)
public class Test {
    @Autowired
    RedisUtil redisUtil;

    @org.junit.Test
    public void test() {
        ValueOperations<String, String> valueOperations = redisUtil.valueOperations();
        System.out.println("1 " + valueOperations.get("name"));
        valueOperations.set("name", "zealot");
        System.out.println("2 set name = zealot");
        System.out.println("3 " + valueOperations.get("name"));
    }

    @org.junit.Test
    public void test2() {
        new Thread(() -> {
            System.out.println(redisUtil.set("abc", "abc", 10000));
        }).start();
        new Thread(() -> {
            System.out.println(redisUtil.set("abc", "abc", 10000));
        }).start();
        System.out.println();

    }

}
