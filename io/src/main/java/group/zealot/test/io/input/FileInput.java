package group.zealot.test.io.input;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

public class FileInput {
    public static void main(String[] args) {
        byte[] bytes = {-26, -120, -112};
        Charset cs = Charset.forName("UTF-8");
        ByteBuffer bb = ByteBuffer.wrap(bytes);
        CharBuffer cb = cs.decode(bb);
        System.out.println(cb);
        System.out.println("end");
    }
}
