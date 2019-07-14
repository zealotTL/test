package group.zealot.test.redis;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class StringInitData extends InitData {
    @Autowired
    RedisUtil redisUtil;

    private  final int testNum = 1000000;

    private Map<String, JSONObject> getMap() {
        Map<String, JSONObject> map = new HashMap<>(testNum);
        int i = 0;
        while (i++ < testNum) {
            JSONObject item = build(i);
            String key = getKey(item);
            map.put(key, item);
        }
        return map;
    }

    public void initData() {
        redisUtil.flushAll();
        Map<String, JSONObject> map = getMap();
        ValueOperations<String, JSONObject> valueOperations = redisUtil.valueOperations();
        valueOperations.multiSet(map);
    }
}
