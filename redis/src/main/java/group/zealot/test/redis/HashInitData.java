package group.zealot.test.redis;

import com.alibaba.fastjson.JSONObject;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class HashInitData extends InitData {
    private final int testNum = 1000000;

    private Map<String, Map<String, JSONObject>> getMaps() {
        Map<String, Map<String, JSONObject>> maps = new HashMap<>(testNum);
        int i = 0;
        int custNum = 1;
        int custId = 0;
        int size = (int) (Math.random() * 100 * 1000);
        while (i++ < testNum) {
            JSONObject item = build(i);
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
        setCustIds(maps.keySet());
        return maps;
    }

    public void initData() {
        redisUtil.flushAll();
        Map<String, Map<String, JSONObject>> maps = getMaps();
        HashOperations<String, String, JSONObject> hashOperations = redisUtil.hashOperations();
        for (Map.Entry<String, Map<String, JSONObject>> entry : maps.entrySet()) {
            hashOperations.putAll(entry.getKey(), entry.getValue());
        }
    }
}
