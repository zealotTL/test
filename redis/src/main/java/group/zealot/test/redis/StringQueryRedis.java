package group.zealot.test.redis;

import com.alibaba.fastjson.JSONObject;
import group.zealot.test.thread.MyThreadPoolManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class StringQueryRedis extends QueryRedis {

    @Override
    protected String getSearchKey(JSONObject item) {
        String key = "*";
        if (item != null) {
            if (item.containsKey("mdn")) {
                key += "mdn*" + item.getString("mdn") + "*";
            }
            if (item.containsKey("custId")) {
                key += "custId*" + item.getString("custId") + "*";
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
    protected ConvertingCursor doScan(JSONObject voo, String custId) {
        voo.put("custId", custId);
        ScanOptions options = buildOptions(voo);
        RedisTemplate redisTemplate = redisUtil.redisTemplate();
        return (ConvertingCursor) redisTemplate.executeWithStickyConnection((RedisConnection connection) -> {
            return new ConvertingCursor<>(connection.scan(options), (byte[] bytes) -> {
                return (JSONObject) redisUtil.deserialize(bytes);
            });
        });
    }

    @Override
    protected JSONObject getFromCursorNext(Object object) {
        if (object instanceof JSONObject) {
            return (JSONObject) object;
        } else {
            throw new RuntimeException("redis get object type is error");
        }
    }

}
