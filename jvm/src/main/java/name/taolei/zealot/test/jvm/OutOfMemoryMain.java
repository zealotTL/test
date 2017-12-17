package name.taolei.zealot.test.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 *
 */
public class OutOfMemoryMain {
    public static void main(String[] args) {
        // outOfMemoryJavaHeapSpace();
        outOfMemoryPermGenSpace();
    }
    
    /**
     * -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails
     * -XX:SurvivorRatio=8
     */
    public static void outOfMemoryJavaHeapSpace() {
        List<Object> list = new ArrayList<Object>();
        while (true) {
            list.add(new Object());
        }
    }
    
    /**
     * -XX:PermSize=10M -XX:MaxpermSize=10M
     */
    public static void outOfMemoryPermGenSpace() {
        List<String> list = new ArrayList<String>();
        long i = 0;
        while (true) {
            list.add(String.valueOf(i++).intern());
        }
    }
}
