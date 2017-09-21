package name.taolei.zealot.test.springboot.activeMQ;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jms.ConnectionFactory;
import javax.naming.NamingException;

import org.apache.activemq.jms.pool.PooledConnectionFactory;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.destination.JndiDestinationResolver;

@Configuration
public class Config {

    @Bean
    public ActiveMQConnectionFactory amqpConnectionFactory() {
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
        return cachingConnectionFactory;
    }

    @Bean
    //队列消息
    public JmsTemplate pubJmsTemplate(CachingConnectionFactory cachingConnectionFactory) {
        return jmsTemplate(cachingConnectionFactory, false);
    }

    @Bean
    //主题消息
    public JmsTemplate subJmsTemplate(CachingConnectionFactory cachingConnectionFactory) {
        return jmsTemplate(cachingConnectionFactory, true);
    }

    private JmsTemplate jmsTemplate(CachingConnectionFactory cachingConnectionFactory, boolean pubSubDomain) {
        JmsTemplate jmsTemplate = new JmsTemplate(cachingConnectionFactory);
        jmsTemplate.setDefaultDestinationName("test-destination");
        // 主题（Topic）和队列消息
        jmsTemplate.setPubSubDomain(true);
        return jmsTemplate;
    }

    @Bean
    public JndiDestinationResolver destinationResolver(){
        JndiDestinationResolver jndiDestinationResolver = new JndiDestinationResolver(){

        }
        return jndiDestinationResolver;
    }

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(
            CachingConnectionFactory cachingConnectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(cachingConnectionFactory);
//        factory.setConcurrency("1-1");

        return factory;
    }
}
