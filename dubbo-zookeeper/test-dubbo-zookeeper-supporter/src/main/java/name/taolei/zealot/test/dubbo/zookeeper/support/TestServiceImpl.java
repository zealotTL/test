package name.taolei.zealot.test.dubbo.zookeeper.support;

import com.alibaba.dubbo.config.annotation.Service;
import name.taolei.zealot.test.dubbo.zookeeper.core.TestEntity;
import name.taolei.zealot.test.dubbo.zookeeper.core.TestService;

@Service
public class TestServiceImpl implements TestService {
    
    public String test(TestEntity testEntity) {
        return Thread.currentThread().getName() + ":" + testEntity.getName();
    }
    
}
