package name.taolei.zealot.test.springboot.activeMQ;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.support.converter.SimpleMessageConverter;

@Configuration
@EnableJms
public class Config {

    @Bean
    public ActiveMQConnectionFactory activeMQConnectionFactory() {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
        activeMQConnectionFactory.setBrokerURL("tcp://localhost:61616");
        activeMQConnectionFactory.setUserName("admin");
        activeMQConnectionFactory.setPassword("admin");
        return activeMQConnectionFactory;
    }

    @Bean
    public CachingConnectionFactory cachingConnectionFactory(ActiveMQConnectionFactory activeMQConnectionFactory) {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
        cachingConnectionFactory.setTargetConnectionFactory(activeMQConnectionFactory);
        cachingConnectionFactory.setSessionCacheSize(100);
        cachingConnectionFactory.setCacheConsumers(true);
        cachingConnectionFactory.setCacheProducers(true);
        return cachingConnectionFactory;
    }


    @Bean
    public SimpleMessageConverter simpleMessageConverter() {
        SimpleMessageConverter simpleMessageConverter = new SimpleMessageConverter();
        return simpleMessageConverter;
    }

    @Bean
    //队列消息
    public JmsTemplate pubJmsTemplate(SimpleMessageConverter simpleMessageConverter,
            CachingConnectionFactory cachingConnectionFactory) {
        return jmsTemplate(simpleMessageConverter, cachingConnectionFactory, false, "test-queue");
    }

    @Bean
    //主题消息
    public JmsTemplate subJmsTemplate(SimpleMessageConverter simpleMessageConverter,
            CachingConnectionFactory cachingConnectionFactory) {
        return jmsTemplate(simpleMessageConverter, cachingConnectionFactory, true, "test-topic");
    }

    private JmsTemplate jmsTemplate(SimpleMessageConverter simpleMessageConverter,
            CachingConnectionFactory cachingConnectionFactory, boolean pubSubDomain, String destinationName) {
        JmsTemplate jmsTemplate = new JmsTemplate(cachingConnectionFactory);
        jmsTemplate.setDefaultDestinationName(destinationName);
        jmsTemplate.setMessageConverter(simpleMessageConverter);
        return jmsTemplate;
    }

    @Bean
    public MyMessageListener myMessageListener() {
        return new MyMessageListener();
    }

    @Bean
    public DefaultMessageListenerContainer topicDefaultMessageListenerContainer(
            CachingConnectionFactory producerCachingConnectionFactory, MyMessageListener myMessageListener) {
        return defaultMessageListenerContainer(producerCachingConnectionFactory, "test-topic", true, myMessageListener);
    }

    @Bean
    public DefaultMessageListenerContainer queueDefaultMessageListenerContainer(
            CachingConnectionFactory producerCachingConnectionFactory, MyMessageListener myMessageListener) {
        return defaultMessageListenerContainer(producerCachingConnectionFactory, "test-queue", false, myMessageListener);
    }

    private DefaultMessageListenerContainer defaultMessageListenerContainer(
            CachingConnectionFactory cachingConnectionFactory, String destinationName, boolean pubSubDomain,
            MyMessageListener myMessageListener) {
        DefaultMessageListenerContainer defaultMessageListenerContainer = new DefaultMessageListenerContainer();
        defaultMessageListenerContainer.setConnectionFactory(cachingConnectionFactory);
        defaultMessageListenerContainer.setDestinationName(destinationName);
        defaultMessageListenerContainer.setMessageListener(myMessageListener);
        defaultMessageListenerContainer.setPubSubDomain(pubSubDomain);
        return defaultMessageListenerContainer;
    }

    @Bean
    public DefaultJmsListenerContainerFactory defaultJmsListenerContainerFactory(CachingConnectionFactory cachingConnectionFactory) {
        DefaultJmsListenerContainerFactory defaultJmsListenerContainerFactory = new DefaultJmsListenerContainerFactory();
        defaultJmsListenerContainerFactory.setConnectionFactory(cachingConnectionFactory);
        return defaultJmsListenerContainerFactory;
    }
}
