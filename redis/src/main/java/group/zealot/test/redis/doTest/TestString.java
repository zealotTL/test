package group.zealot.test.redis.doTest;

import com.alibaba.fastjson.JSONObject;
import group.zealot.test.redis.Main;
import group.zealot.test.redis.RedisUtil;
import group.zealot.test.thread.MyThreadPoolManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Main.class)
public class TestString {
    protected Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    RedisUtil redisUtil;

    //    @Before
    public void init() {
        logger.info("start");
        RedisTemplate<String, JSONObject> redisTemplate = redisUtil.redisTemplate();
        int i = 0;
        JSONObject item = new JSONObject();
        MyThreadPoolManager pool = MyThreadPoolManager.getInstance();
        while (i < 2000000) {
            item.put("mdn", i);
            redisTemplate.opsForValue().set("mdn:" + i, item, 12, TimeUnit.HOURS);
            i++;
            if (i % 100000 == 0) {
                logger.info("i");
            }
        }
        logger.info("end");
    }

    @Test
    public void test() {
        RedisTemplate<String, JSONObject> redisTemplate = redisUtil.redisTemplate();
        logger.info("start");
        Set<String> set = redisTemplate.keys("*");
        logger.info("end size:" + set.size());

    }

}
