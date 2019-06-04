package group.zealot.test.io;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Run.class})
public class FilePathMain {

    /**
     * resources 目录下文件读取
     */
    @Test
    public void resourcesPath() {
        URL url = getClass().getResource("/resourcesPathFile");
        try (InputStream in = Run.toInputStream(url)) {
            int i;
            while ((i = in.read()) != -1) {
                System.out.print(i + " ");
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 读取打包后classes路径下的文件
     */
    @Test
    public void classLoadPath1() {
        String di = System.getProperty("file.separator");
        URL url = getClass().getClassLoader().getResource("group" + di + "zealot" + di + "test" + di + "io" + di + "classLoadPathFile");
        Charset charset = Charset.forName("UTF-8");
        try (BufferedReader bf = Run.toBufferedReader(url, charset)) {
            Run.loggerChar(bf);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 读取打包后classes路径下的文件
     */
    @Test
    public void classLoadPath2() {
        URL url = getClass().getClassLoader().getResource("classLoadPathFile");
        Charset charset = Charset.forName("UTF-8");
        try (BufferedReader bf = Run.toBufferedReader(url, charset)) {
            Run.loggerChar(bf);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
