package group.zealot.test.redis;

import com.alibaba.fastjson.JSONObject;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class StringInitData extends InitData {

    private final int testNum = 1000000;

    private List<Map<String, JSONObject>> getMaps() {
        List<Map<String, JSONObject>> maps = new ArrayList<>();
        Set<String> custIds = new HashSet<>();
        Map<String, JSONObject> map = new HashMap<>(100000);
        int i = 0;
        while (i++ < testNum) {
            JSONObject item = build(i);
            custIds.add(item.getString("custId"));
            String key = getKey(item);
            map.put(key, item);
            if (map.size() == 100000) {
                maps.add(map);
                map = new HashMap<>(100000);
            }
        }
        if (!map.isEmpty()) {
            maps.add(map);
        }
        setCustIds(custIds);
        return maps;
    }

    public void initData() {
        redisUtil.flushAll();
        List<Map<String, JSONObject>> maps = getMaps();
        ValueOperations<String, JSONObject> valueOperations = redisUtil.valueOperations();
        for (Map<String, JSONObject> map : maps) {
            valueOperations.multiSet(map);
        }
    }
}
