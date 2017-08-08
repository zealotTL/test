package name.taolei.zealot.test.springboot.conditional.impl;

import name.taolei.zealot.test.springboot.conditional.TestService;

public class WindowsTestService implements TestService {
    @Override public void doSomething() {
        System.out.println("Windows系统");
    }
}
