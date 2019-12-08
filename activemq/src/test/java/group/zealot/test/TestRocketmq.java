package group.zealot.test;

import com.alibaba.fastjson.JSONObject;
import group.zealot.test.mq.rocketmq.AbsConsumer;
import group.zealot.test.mq.rocketmq.RocketMqUtil;
import org.springframework.stereotype.Component;

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
        return "Test";
    }
}
