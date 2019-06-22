package group.zealot.test.druid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/timeout")
public class TimeoutController {


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @RequestMapping("/test")
    @ResponseBody
    public String test() {
        System.out.println("start " + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        jdbcTemplate.execute("update tb_user  set name = '2' where id =1");
        System.out.println("end " + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        return "success";
    }
}
