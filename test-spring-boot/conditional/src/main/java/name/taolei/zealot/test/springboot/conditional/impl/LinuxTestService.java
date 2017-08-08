package name.taolei.zealot.test.springboot.conditional.impl;

import name.taolei.zealot.test.springboot.conditional.TestService;

public class LinuxTestService implements TestService {
    @Override public void doSomething() {
        System.out.println("Linux系统");
    }
}
