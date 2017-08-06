package name.taolei.zealot.test.springboot.logback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public  class LogbackHelper {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    public void helpMethod(){
        logger.debug("This is a debug message");
        logger.info("This is an info message");
        logger.warn("This is a warn message");
        logger.error("This is an error message");
    }
    public static  void main(String[] ags){
        LogbackHelper LogbackHelper = new LogbackHelper();
        for (int i =0;i<10;i++)
        LogbackHelper.helpMethod();
    }
}
