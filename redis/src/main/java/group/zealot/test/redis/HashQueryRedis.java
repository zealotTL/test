package group.zealot.test.redis;

import com.alibaba.fastjson.JSONObject;
import group.zealot.test.thread.MyThreadPoolManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class HashQueryRedis extends QueryRedis {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    protected String getSearchKey(JSONObject item) {
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

    @Override
    protected Cursor doScan(JSONObject voo, String custId) {
        ScanOptions options = buildOptions(voo);

        RedisTemplate redisTemplate = redisUtil.redisTemplate();
        return (ConvertingCursor) redisTemplate.executeWithStickyConnection((RedisConnection connection) -> {
            return new ConvertingCursor<>(connection.hScan(redisUtil.serializer(custId), options), (Map.Entry<byte[], byte[]> entry) -> {
                Map.Entry<String, JSONObject> newEntry =
                        new HashMap.SimpleEntry<>(
                                (String) redisUtil.deserialize(entry.getKey()), (JSONObject) redisUtil.deserialize(entry.getValue()));
                return newEntry;
            });
        });
    }

    @Override
    protected JSONObject getFromCursorNext(Object object) {
        if (object instanceof Map.Entry) {
            Map.Entry<String, JSONObject> entry = (Map.Entry<String, JSONObject>) object;
            return entry.getValue();
        } else {
            throw new RuntimeException("redis get object type is error");
        }
    }
}
