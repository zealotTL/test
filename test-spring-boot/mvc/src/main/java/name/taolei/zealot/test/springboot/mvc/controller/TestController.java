package name.taolei.zealot.test.springboot.mvc.controller;

import name.taolei.zealot.test.springboot.mvc.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class TestController {
    @Autowired
    private TestService testService;
    @RequestMapping("/")
    public String greeting() {
        return testService.doSomething();
    }
}