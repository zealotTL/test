package group.zealot.test;

import com.alibaba.fastjson.JSONObject;
import group.zealot.test.mq.rocketmq.AbsConsumer;
import group.zealot.test.mq.rocketmq.RocketMqUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
public class TestRocketmq extends AbsConsumer {
    public TestRocketmq(RocketMqUtil rocketMqUtil) {
        super(rocketMqUtil);
    }

    @Override
    protected void deal(JSONObject message) {
        logger.info(message.toJSONString());
    }

    @Override
    protected String getTopic(){
        return "Test2";
    }



    @Autowired(required = false)
    RocketMqUtil rocketMqUtil;

    @Async
    public void sendAsync(CountDownLatch latch){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name1", "taoleitaoleitaolei");
        jsonObject.put("name2", "taoleitaoleitaolei");
        jsonObject.put("name3", "taoleitaoleitaolei");
        jsonObject.put("name4", "taoleitaoleitaolei");
        jsonObject.put("name5", "taoleitaoleitaolei");
        jsonObject.put("name6", "taoleitaoleitaolei");
        jsonObject.put("name7", "taoleitaoleitaoleitaoleitaoleitaoleitaoleitaoleitaolei");
        jsonObject.put("name8", "taoleitaoleitaoleitaoleitaoleitaoleitaoleitaoleitaolei");
        jsonObject.put("name9", "taoleitaoleitaoleitaoleitaoleitaoleitaoleitaoleitaolei");
        rocketMqUtil.send("Test", jsonObject);
        latch.countDown();
    }
}
