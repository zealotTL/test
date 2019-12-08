package group.zealot.test.mq.rocketmq;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RocketmqConfig {

    @Value("${rocketmq.namesrv-addr}")
    private String namesrvAddr;
    @Value("${rocketmq.producer-group}")
    private String producerGroup;

    @Bean
    public DefaultMQProducer defaultMQProducer() throws MQClientException {
        DefaultMQProducer producer = new DefaultMQProducer();
        producer.setNamesrvAddr(namesrvAddr);
        producer.setProducerGroup(producerGroup);
        producer.start();
        return producer;
    }
}
