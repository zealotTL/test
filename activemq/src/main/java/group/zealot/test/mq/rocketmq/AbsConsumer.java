package group.zealot.test.mq.rocketmq;

import com.alibaba.fastjson.JSONObject;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import java.util.List;

public abstract class AbsConsumer {
    private static final String topic = "default";
    private static final String subExpression = "*";
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${rocketmq.namesrv-addr}")
    private String namesrvAddr;
    @Value("${rocketmq.consumer-group}")
    private String consumerGroup;

    private DefaultMQPushConsumer consumer;
    private RocketMqUtil rocketMqUtil;

    protected AbsConsumer(RocketMqUtil rocketMqUtil) {
        super();
        this.rocketMqUtil = rocketMqUtil;
        this.consumer = new DefaultMQPushConsumer();
    }

    @PostConstruct
    public void init() throws MQClientException {
        consumer.setNamesrvAddr(namesrvAddr);
        consumer.setConsumerGroup(consumerGroup);
        consumer.setVipChannelEnabled(false);
        consumer.subscribe(getTopic(), getSubExpression());
        consumer.setConsumeMessageBatchMaxSize(1);
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                try {
                    if (msgs.size() != 1) {
                        logger.error("获取消息长度异常，应该是" + consumer.getConsumeMessageBatchMaxSize() + " 当前消息队列长度：" + msgs.size());
                        logger.error("mags：" + JSONObject.toJSONString(msgs));
                        logger.error("context：" + JSONObject.toJSONString(context));
                    }
                    JSONObject message = rocketMqUtil.toJSONObject(msgs.get(0));
                    logger.debug("message：”" + message.toJSONString());
                    deal(message);
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                } catch (Exception e) {
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }
            }
        });
        consumer.start();
    }


    protected abstract void deal(JSONObject message);

    protected String getTopic() {
        return AbsConsumer.topic;
    }

    protected String getSubExpression() {
        return AbsConsumer.subExpression;
    }
}
