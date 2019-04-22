package group.zealot.test.activity;

import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class Run {
    public static void main(String[] args) {
        SpringApplication.run(Run.class, args);
    }
}
