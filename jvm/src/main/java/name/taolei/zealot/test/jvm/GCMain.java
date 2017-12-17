package name.taolei.zealot.test.jvm;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

public class GCMain {
    
    public static void main(String[] args) {
//        referenceCounting();
        fourReference();
    }
    
    /**
     * ParOldGen
     */
    public static void referenceCounting() {
        ReferenceCountingGC a = new ReferenceCountingGC();
        ReferenceCountingGC b = new ReferenceCountingGC();
        a.instance = b;
        b.instance = a;
        System.gc();
        System.out.println(a.bigSize);
        a = null;
        b = null;
        System.gc();
    }
    
    public static void fourReference() {
        ReferenceCountingGC a = new ReferenceCountingGC();
        System.gc();
        SoftReference<ReferenceCountingGC> b = new SoftReference<ReferenceCountingGC>(new ReferenceCountingGC());
        System.gc();
        WeakReference<ReferenceCountingGC> c = new WeakReference<ReferenceCountingGC>(new ReferenceCountingGC());
        System.gc();
        ReferenceQueue<ReferenceCountingGC> refQueue = new ReferenceQueue<ReferenceCountingGC>();
        PhantomReference<ReferenceCountingGC> d = new PhantomReference<ReferenceCountingGC>(new ReferenceCountingGC(),
                refQueue);
        System.gc();
    }
}

class ReferenceCountingGC {
    public Object instance;
    private static final int _1MB = 1024 * 1024 * 10;
    public byte[] bigSize = new byte[2 * _1MB];
}
