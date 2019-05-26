package group.zealot.test.redis;

import com.alibaba.fastjson.JSONObject;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Main.class)
public class TestSearch {
    @Autowired
    RedisUtil redisUtil;

    @org.junit.Test
    public void test() {
        initData();


        Set<Object> set = redisUtil.redisTemplate().keys("*\"mk\":\"*一*\"*");
        System.out.println(set.size());
    }

    private void initData() {
        {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", 1);
            jsonObject.put("name", "taolei");
            jsonObject.put("age", "15");
            jsonObject.put("sex", "男");
            jsonObject.put("date", new Date());
            jsonObject.put("fg", true);
            jsonObject.put("mk", "我是一个程序员");
            System.out.println(jsonObject.toJSONString());
            redisUtil.valueOperations().set(jsonObject.toJSONString(), true);

        }
        {

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", 2);
            jsonObject.put("name", "shengyaping");
            jsonObject.put("age", "25");
            jsonObject.put("sex", "女");
            jsonObject.put("mk", "我是一个测试员");
            System.out.println(jsonObject.toJSONString());
            redisUtil.valueOperations().set(jsonObject.toJSONString(), true);

        }
    }
}
