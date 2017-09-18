package name.taolei.zealot.test.transacion;

public abstract class TestThread implements Runnable {
    protected TestService testService;
    protected TestService2 testService2;

    public TestThread(TestService testService) {
        this.testService = testService;
    }
    public TestThread(TestService2 testService2) {
        this.testService2 = testService2;
    }
}
