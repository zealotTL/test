package name.taolei.zealot.test.springboot.logback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public void helpMethod() {
        logger.debug("This is a debug message");
        logger.info("This is an info message");
        logger.warn("This is a warn message");
        logger.error("This is an error message");
    }

    public static void main(String[] ags) {
        Main Main = new Main();
        for (int i = 0; i < 10; i++) {
            Main.helpMethod();
        }
        System.out.println("asdfasdfadsf");
    }
}
