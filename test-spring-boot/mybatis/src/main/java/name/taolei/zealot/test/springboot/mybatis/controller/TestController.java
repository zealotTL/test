package name.taolei.zealot.test.springboot.mybatis.controller;

import name.taolei.zealot.test.springboot.mybatis.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Autowired
    private TestService testService;
    @RequestMapping("/")
    public String greeting() {
        return testService.doSomething();
    }
}
