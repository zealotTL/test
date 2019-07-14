package group.zealot.test.redis;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class InitData {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    protected SimpleDateFormat sdf = new SimpleDateFormat("mmssSSS");

    public String getKey(JSONObject item) {
        String key = "";
        key += "mdn" + item.getString("mdn") + "";
        key += "custId" + item.getString("custId") + "";
        key += "status" + item.getString("status") + "";
        key += "iccid" + item.getString("iccid") + "";
        key += "groupName" + item.getString("groupName") + "";
        return key;
    }

    public JSONObject build(int i) {
        JSONObject item = new JSONObject();
        item.put("custId", "" + (int) (Math.random() * 10));
        item.put("mdn", "1490000" + i);
        item.put("status", "" + i % 5);
        item.put("iccid", "8086" + sdf.format(new Date()) + (int) (Math.random() * 100000000));
        item.put("groupName", "abcdef".substring(i % 5));
        return item;
    }

    abstract protected void initData();
}
