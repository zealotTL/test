package group.zealot.test.dubbo;

import org.apache.dubbo.config.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Main {
    @Bean("testService")
    public TestService testService(ApplicationConfig applicationConfig, RegistryConfig registryConfig) {
        ReferenceConfig<TestService> reference = new ReferenceConfig<>();
        reference.setApplication(applicationConfig);
        reference.setRegistry(registryConfig);
        reference.setInterface(TestService.class);
        reference.setVersion("1.0.0");
        return reference.get();
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);
        TestService testService = context.getBean("testService", TestService.class);
        testService.get();
    }

}
