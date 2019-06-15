package group.zealot.test.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQSession;
import org.apache.activemq.RedeliveryPolicy;
import org.messaginghub.pooled.jms.JmsPoolConnectionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;

@SpringBootApplication
@EnableJms
public class Main {

    @Bean
    public DefaultJmsListenerContainerFactory defaultJmsListenerContainerFactory(JmsPoolConnectionFactory jmsPoolConnectionFactory) {
        ActiveMQConnectionFactory factory = (ActiveMQConnectionFactory) jmsPoolConnectionFactory.getConnectionFactory();
        RedeliveryPolicy redeliveryPolicy = factory.getRedeliveryPolicy();
        redeliveryPolicy.setUseExponentialBackOff(false);//重发时间间隔递增
        redeliveryPolicy.setBackOffMultiplier(1.0);//重发时间间隔倍数增长，需 UseExponentialBackOff 为true
        redeliveryPolicy.setMaximumRedeliveries(1);//最大重发次数
        redeliveryPolicy.setMaximumRedeliveryDelay(10000);//最大重发间隔时间
        redeliveryPolicy.setInitialRedeliveryDelay(2000);//初始重发时间间隔

        DefaultJmsListenerContainerFactory defaultJmsListenerContainerFactory = new DefaultJmsListenerContainerFactory();
        defaultJmsListenerContainerFactory.setConnectionFactory(jmsPoolConnectionFactory);
        defaultJmsListenerContainerFactory.setSessionAcknowledgeMode(ActiveMQSession.INDIVIDUAL_ACKNOWLEDGE);//客户端调用acknowledge方法手动签收  activemq专用4
        defaultJmsListenerContainerFactory.setPubSubDomain(false);
        return defaultJmsListenerContainerFactory;
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
