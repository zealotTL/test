package group.zealot.test.io;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


public class Run {

    public static Path path;

    static {
        try {
            path = Paths.get(Run.class.getResource("/abc").toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println(Files.size(path));
        print(Files.readAllBytes(path));
        List<String> list = Files.readAllLines(path);
        System.out.println("========");
        for (String str : list) {
            System.out.println(str);
        }
        System.out.println("========");
        byte[] bytes = {-26, -120, -112};
        System.out.println(new String(bytes));

        Charset cs = Charset.forName("UTF-8");

        ByteBuffer bb = ByteBuffer.wrap(bytes);
        CharBuffer cb = cs.decode(bb);
        System.out.println(cb);
        System.out.println("end");
    }

    static void print(byte[] os) {
        for (byte o : os) {
            System.out.print(o + " ");
        }
    }
}
