package group.zealot.test.redis;

import com.alibaba.fastjson.JSONObject;
import group.zealot.test.thread.MyThreadPoolManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Main.class)
public class TestOrderSearch {
    protected Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    RedisUtil redisUtil;

    private Map<String, Map<String, JSONObject>> getMaps() {
        Map<String, Map<String, JSONObject>> maps = new HashMap<>(5000000);
        SimpleDateFormat sdf = new SimpleDateFormat("mmssSSS");
        int i = 0;
        int custNum = 1;
        int custId = 0;
        int size = (int) (Math.random() * 100 * 1000);
        while (i++ < 5000000) {
            JSONObject item = new JSONObject();
            item.put("custId", "" + custId);
            item.put("mdn", "1490000" + i);
            item.put("status", "" + i % 5);
            item.put("iccid", "8086" + sdf.format(new Date()) + (int) (Math.random() * 100000000));
            item.put("groupName", "abcdef".substring(i % 5));

            if (size == custNum) {
                custNum = 1;
                custId += 1;
                size = (int) (Math.random() * 100 * 1000);
            } else {
                custNum += 1;
            }

            String key = getKey(item);
            if (!maps.containsKey(custId + "")) {
                maps.put(custId + "", new HashMap<>());
            }
            maps.get(custId + "").put(key, item);
        }
        logger.info(custId + " = ");
        return maps;
    }

    private String getKey(JSONObject item) {
        String key = "";
        key += "mdn" + item.getString("mdn") + "";
        key += "status" + item.getString("status") + "";
        key += "iccid" + item.getString("iccid") + "";
        key += "groupName" + item.getString("groupName") + "";
        return key;
    }

    private String getSearchKey(JSONObject item) {
        String key = "*";
        if (item != null) {
            if (item.containsKey("mdn")) {
                key += "mdn*" + item.getString("mdn") + "*";
            }
            if (item.containsKey("status")) {
                key += "status" + item.getString("status") + "";
            }
            if (item.containsKey("iccid")) {
                key += "iccid*" + item.getString("iccid") + "*";
            }
            if (item.containsKey("groupName")) {
                key += "groupName*" + item.getString("groupName") + "*";

            }
        }
        key += "*";
        key = key.replaceAll("\\*\\*", "\\*");
        return key;
    }

    //    @Before
    public void initHashData() {
        redisUtil.flushAll();
        Map<String, Map<String, JSONObject>> maps = getMaps();
        HashOperations<String, String, JSONObject> hashOperations = redisUtil.hashOperations();
        for (Map.Entry<String, Map<String, JSONObject>> entry : maps.entrySet()) {
            hashOperations.putAll(entry.getKey(), entry.getValue());
        }
    }

    //    @Before
    public void initStringData() {
        redisUtil.flushAll();
        Map<String, Map<String, JSONObject>> maps = getMaps();
        HashOperations<String, String, JSONObject> hashOperations = redisUtil.hashOperations();
        for (Map.Entry<String, Map<String, JSONObject>> entry : maps.entrySet()) {
            hashOperations.putAll(entry.getKey(), entry.getValue());
        }
    }

    @Test
    public void test() {
        logger.info("start");
        JSONObject vo = new JSONObject();
        vo.put("mdn", "12345");
//        vo.put("status", "2");
//        vo.put("custId", "2");
        List<String> custIds = new ArrayList<>();
        vo.put("custIds", custIds);
        List<JSONObject> list = queryHash(vo, 1, 20);
        logger.info("end");

    }

    private List<JSONObject> queryHash(JSONObject vo, int posStart, int posEnd) {
        List<JSONObject> list = new ArrayList<>();

        HashOperations<String, String, JSONObject> hashOperations = redisUtil.hashOperations();

        ScanOptions.ScanOptionsBuilder builder = new ScanOptions.ScanOptionsBuilder();
        builder.match(getSearchKey(vo));
        builder.count(100);
        ScanOptions options = builder.build();
        logger.info(getSearchKey(vo));

        Set<String> custIds;
        if (vo.containsKey("custId")) {
            List<String> temp = new ArrayList<>();
            temp.add(vo.getString("custId"));
            vo.put("custIds", temp);
        }
        custIds = queryHashKeys(vo.getObject("custIds", List.class));

        MyThreadPoolManager manager = MyThreadPoolManager.getInstance();
        final AtomicInteger i = new AtomicInteger(0);
        CountDownLatch latch = new CountDownLatch(custIds.size());
        for (String custId : custIds) {
            manager.execute(() -> {
                if (i.get() > posEnd) {
                    latch.countDown();
                    return;
                }
                Cursor<Map.Entry<String, JSONObject>> cursor = hashOperations.scan(custId, options);
                try {
                    while (cursor.hasNext()) {
                        Map.Entry<String, JSONObject> entry = cursor.next();
                        i.incrementAndGet();
                        if (i.get() >= posStart && i.get() <= posEnd) {
                            logger.info(entry.getKey() + " : " + entry.getValue().toJSONString());
                            synchronized (list) {
                                list.add(entry.getValue());
                            }
                        } else if (i.get() > posEnd) {
                            break;
                        }
                    }
                } finally {
                    latch.countDown();
                    try {
                        cursor.close();
                    } catch (IOException e) {
                        logger.error("IOException", e);
                    }
                }
            });
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            logger.error("InterruptedException", e);
        }
        return list;
    }

    private Set<String> queryHashKeys(List<String> custIds) {
        RedisTemplate<String, Object> redisTemplate = redisUtil.redisTemplate();
        Set<String> set = redisTemplate.keys("*");
        if (custIds != null && !custIds.isEmpty()) {
            set.retainAll(custIds);
        }
        return set;
    }
}
