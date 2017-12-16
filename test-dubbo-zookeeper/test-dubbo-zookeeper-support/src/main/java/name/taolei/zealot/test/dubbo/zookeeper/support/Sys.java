package name.taolei.zealot.test.dubbo.zookeeper.support;

import com.alibaba.dubbo.config.annotation.Service;
import name.taolei.zealot.test.dubbo.zookeeper.core.TestEntity;
import name.taolei.zealot.test.dubbo.zookeeper.core.TestInterface;

@Service
public class Sys implements TestInterface {
    
    public String test(TestEntity testEntity) {
        return Thread.currentThread().getName() + ":" + testEntity.getName();
    }
    
}
