package group.zealot.test.dubbo;

import org.apache.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service(timeout = 5000, interfaceClass = TestService.class)
public class TestServiceImpl implements TestService {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public String get() {
        logger.info("get方法被调用");
        return "TestServiceImpl";
    }
}
