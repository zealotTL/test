package group.zealot.test.druid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@SpringBootApplication
//@RestController
public class Main {

    /**
     * /druid/datasource.html
     */
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

//    @RequestMapping("/simpleQuary")
//    @ResponseBody
    public String simpleQuary() {
        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        for (int i = 0; i < 10000; i++) {
            String sql = "insert into tb_user (name,age,sex,insert_time) values ('%s',%d,'%s','%s')";
            sql = String.format(sql, getRandomString(((Double) (10 * Math.random())).intValue()), ((Double) (100 * Math.random())).intValue(), "男", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            jdbcTemplate.execute(sql);
        }
        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        return "success";
    }

    public static String getRandomString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }
}
