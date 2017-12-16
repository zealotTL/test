package name.taolei.zealot.test.dubbo.zookeeper.user;

import com.alibaba.dubbo.config.annotation.Reference;
import name.taolei.zealot.test.dubbo.zookeeper.core.TestEntity;
import name.taolei.zealot.test.dubbo.zookeeper.core.TestService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("/")
public class Run {
    @Reference
    private TestService testService;

    @RequestMapping("/123")
    public String test() {
        TestEntity testEntity = new TestEntity();
        testEntity.setId(123);
        testEntity.setName("taolei");
        System.out.println(testService.test(testEntity));
        return null;
    }

}
