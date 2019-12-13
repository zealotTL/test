package group.zealot.test.mq.rocketmq;

import com.alibaba.fastjson.JSONObject;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.MQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class RocketMqUtil {
    private static final String DEFAULT_TAG = "default_tag";

    private Logger logger = LoggerFactory.getLogger(getClass());

    private final SendResult DEFAULT_RESULT;

    private final MQProducer producer;

    public RocketMqUtil(MQProducer producer) {
        this.producer = producer;
        this.DEFAULT_RESULT = new SendResult();
        this.DEFAULT_RESULT.setSendStatus(SendStatus.FLUSH_DISK_TIMEOUT);
    }

    public boolean send(String topic, JSONObject jsonObject) {
        return send(toMessage(topic, DEFAULT_TAG, jsonObject));
    }

    public boolean send(String topic, String tag, JSONObject jsonObject) {
        return send(toMessage(topic, tag, jsonObject));
    }

    protected Message toMessage(String topic, String tag, JSONObject jsonObject) {
        Message message = new Message();
        message.setTopic(topic);
        message.setTags(tag);
        message.setBody(jsonObject.toJSONString().getBytes(StandardCharsets.UTF_8));
        logger.debug(message.toString());
        return message;
    }

    protected JSONObject toJSONObject(Message message) {
        logger.debug("消息：" + message);
        return JSONObject.parseObject(new String(message.getBody(), StandardCharsets.UTF_8));
    }

    private boolean send(Message message) {
        SendStatus status = getSendStatus(message);
        return status == SendStatus.SEND_OK;
    }

    private SendStatus getSendStatus(Message message) {
        SendResult sendResult = getSendResult(message);
        return sendResult.getSendStatus();
    }

    private SendResult getSendResult(Message message) {
        try {
            return producer.send(message,3000L);
        } catch (MQClientException e) {
            logger.error("MQClientException", e);
        } catch (RemotingException e) {
            logger.error("RemotingException", e);
        } catch (MQBrokerException e) {
            logger.error("MQBrokerException", e);
        } catch (InterruptedException e) {
            logger.error("InterruptedException", e);
        }
        return DEFAULT_RESULT;
    }
}
