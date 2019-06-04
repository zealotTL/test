package group.zealot.test.redis;

import io.lettuce.core.SetArgs;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.cluster.api.async.RedisAdvancedClusterAsyncCommands;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

@Component
public class RedisUtil {
    @Autowired
    private LettuceConnectionFactory connectionFactory;

    private RedisConnection redisConnection() {
        return connectionFactory.getConnection();
    }

    private JdkSerializationRedisSerializer serializer = new JdkSerializationRedisSerializer();

    public <K, V> RedisTemplate<K, V> redisTemplate() {
        RedisTemplate<K, V> template = new RedisTemplate<>();
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(serializer);
        template.setConnectionFactory(connectionFactory);
        template.afterPropertiesSet();
        return template;
    }

    public <K, V> RedisTemplate<K, V> redisTemplate(RedisSerializer keySerializer, RedisSerializer valueSerializer) {
        RedisTemplate<K, V> template = new RedisTemplate<>();
        template.setKeySerializer(keySerializer);
        template.setValueSerializer(valueSerializer);
        template.setConnectionFactory(connectionFactory);
        template.afterPropertiesSet();
        return template;
    }

    public byte[] serializer(Object object) {
        return serializer.serialize(object);
    }

    public Object deserialize(byte[] bytes) {
        return serializer.deserialize(bytes);
    }

    public <K, V> ZSetOperations<K, V> zSetOperations() {
        RedisTemplate<K, V> redisTemplate = redisTemplate();
        return redisTemplate.opsForZSet();
    }

    public <K, HK, HV> HashOperations<K, HK, HV> hashOperations() {
        RedisTemplate<K, HV> redisTemplate = redisTemplate();
        return redisTemplate.opsForHash();
    }

    public <K, V> ValueOperations<K, V> valueOperations() {
        RedisTemplate<K, V> template = redisTemplate();
        return template.opsForValue();
    }

    public String set(String keyName, String keyValue, long time) {
        Object nativeConnection = redisConnection().getNativeConnection();
        if (nativeConnection instanceof RedisAsyncCommands) {
            RedisAsyncCommands<Object,Object> commands = (RedisAsyncCommands) nativeConnection;
            //同步方法执行、setnx禁止异步
            return commands
                    .getStatefulConnection()
                    .sync()
                    .set(keyName.getBytes(), keyValue.getBytes(), SetArgs.Builder.nx().ex(time));

        }// lettuce连接包下 redis 集群模式setnx
        if (nativeConnection instanceof RedisAdvancedClusterAsyncCommands) {
            RedisAdvancedClusterAsyncCommands<Object,Object> clusterAsyncCommands = (RedisAdvancedClusterAsyncCommands) nativeConnection;
            return clusterAsyncCommands
                    .getStatefulConnection()
                    .sync()
                    .set(keyName.getBytes(), keyValue.getBytes(), SetArgs.Builder.nx().ex(time));
        }
        return null;
    }

    /**
     * 清空所有key【慎用】
     */
    public void flushAll() {
        RedisConnection redisConnection = redisConnection();
        redisConnection.flushAll();
    }

}
