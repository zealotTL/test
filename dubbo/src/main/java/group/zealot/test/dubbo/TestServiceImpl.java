package group.zealot.test.dubbo;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class TestServiceImpl implements TestService {
    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ApplicationConfig applicationConfig;
    @Autowired
    private RegistryConfig registryConfig;
    @Autowired
    private ProtocolConfig protocolConfig;

    @Override
    public String get() {
        logger.info("get方法被调用");
        return "TestServiceImpl";
    }

    @PostConstruct
    public void init() {
        ServiceConfig<TestService> service = new ServiceConfig<>();
        service.setApplication(applicationConfig);
        service.setRegistry(registryConfig);
        service.setProtocol(protocolConfig);
        service.setInterface(TestService.class);
        service.setRef(this);//非spring代理类
        service.setVersion("1.0.0");
        service.export();
    }
}
