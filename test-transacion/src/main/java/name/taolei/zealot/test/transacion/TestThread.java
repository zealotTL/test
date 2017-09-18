package name.taolei.zealot.test.transacion;

public abstract class TestThread implements Runnable {
    protected TestService testService;

    public TestThread(TestService testService) {
        this.testService = testService;
    }
}
