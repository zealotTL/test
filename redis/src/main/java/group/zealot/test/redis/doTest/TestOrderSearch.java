package group.zealot.test.redis.doTest;

import com.alibaba.fastjson.JSONObject;
import group.zealot.test.redis.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Main.class)
public class TestOrderSearch {
    protected Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    HashQueryRedis hashQueryRedis;
    @Autowired
    HashInitData hashInitData;
    @Autowired
    StringInitData stringInitData;
    @Autowired
    StringQueryRedis stringQueryRedis;

    private JSONObject queryVo() {
        JSONObject vo = new JSONObject();
        vo.put("mdn", "12345");
//        vo.put("status", "2");
//        vo.put("custId", "2");
        List<String> custIds = new ArrayList<>();
        vo.put("custIds", custIds);
        return vo;
    }

    private void sys(List<JSONObject> list) {
        logger.info("list.size=" + list.size());
        for (JSONObject item : list) {
            logger.info(item.toJSONString());
        }
    }

    @Test
    public void testHash() {
        hashInitData.initData();
        logger.info("start");
        List<JSONObject> list = hashQueryRedis.query(queryVo(), 1, 20);
        sys(list);
        logger.info("end");
    }

    @Test
    public void testString() {
        stringInitData.initData();
        logger.info("start");
        List<JSONObject> list = stringQueryRedis.query(queryVo(), 1, 20);
        sys(list);
        logger.info("end");
    }


}
