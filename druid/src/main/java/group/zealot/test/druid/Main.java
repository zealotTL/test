package group.zealot.test.druid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Main {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @RequestMapping("/simpleQuary")
    @ResponseBody
    public String simpleQuary() {
        String sql = "SELECT * FROM tb_druid_1 ";
        jdbcTemplate.execute(sql);
        return "success";
    }
}
