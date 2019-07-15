package group.zealot.test.redis;

import com.alibaba.fastjson.JSONObject;
import group.zealot.test.thread.MyThreadPoolManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class QueryRedis {
    protected Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    protected RedisUtil redisUtil;

    public List<JSONObject> query(JSONObject vo, int posStart, int posEnd) {
        List<JSONObject> list = new ArrayList<>();
        Set<String> custIds = getCustIdsKeys(vo);

        MyThreadPoolManager manager = MyThreadPoolManager.getInstance();
        final AtomicInteger i = new AtomicInteger(0);
        CountDownLatch latch = new CountDownLatch(custIds.size());
        for (String custId : custIds) {
            JSONObject voo = (JSONObject) vo.clone();
            manager.execute(() -> {
                if (i.get() > posEnd) {
                    latch.countDown();
                    return;
                }
                scan(list, voo, posStart, posEnd, i, custId);
                latch.countDown();
            });
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            logger.error("InterruptedException", e);
        }
        return list;

    }

    protected void scan(List<JSONObject> list, JSONObject voo, int posStart, int posEnd, AtomicInteger i, String custId) {
        Cursor cursor = doScan(voo, custId);
        try {
            while (cursor.hasNext()) {
                JSONObject item = getFromCursorNext(cursor.next());
                i.incrementAndGet();
                if (i.get() >= posStart && i.get() <= posEnd) {
                    synchronized (list) {
                        list.add(item);
                    }
                } else if (i.get() > posEnd) {
                    break;
                }
            }
        } finally {
            try {
                cursor.close();
            } catch (IOException e) {
                logger.error("IOException", e);
            }
        }
    }

    abstract protected JSONObject getFromCursorNext(Object object);

    public Set<String> getCustIdsKeys(JSONObject vo) {
        if (vo.containsKey("custId")) {
            List<String> temp = new ArrayList<>();
            temp.add(vo.getString("custId"));
            vo.put("custIds", temp);
        }
        List<String> custIds = vo.getObject("custIds", List.class);

        ValueOperations<String, Set<String>> valueOperations = redisUtil.valueOperations();
        Set<String> set = valueOperations.get("CUST_IDs");
        if (custIds != null && !custIds.isEmpty()) {
            set.retainAll(custIds);
        }
        return set;
    }

    abstract protected String getSearchKey(JSONObject item);

    abstract protected Cursor doScan(JSONObject voo, String custId);

    protected ScanOptions buildOptions(JSONObject vo) {
        logger.info("getSearchKey:  " + getSearchKey(vo));
        ScanOptions options = ScanOptions.scanOptions().match(getSearchKey(vo)).count(100).build();
        return options;
    }

}
