package group.zealot.test.jvm.referenceGC;

public class ReferenceCountingGC {
    public Object instance;
    private static final int _1MB = 1024 * 1024 * 10;
    public byte[] bigSize = new byte[2 * _1MB];
}
