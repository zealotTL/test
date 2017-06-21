package name.taolei.zealot.test.redis;

import java.util.HashMap;
import java.util.Map;

import redis.clients.jedis.Jedis;

public class App {
    public static void main(String[] args) {
        Jedis jedis = RedisUtil.getJedis();
        Map<String, String> map = new HashMap<String, String>();
        map.put("name", "taolei");
        map.put("sex", "male");
        map.put("a", "1");
        map.put("b", "2");
        map.put("c", "3");
        jedis.hmset("user", map);
        System.out.println(jedis.hgetAll("user"));
        
    }
}
