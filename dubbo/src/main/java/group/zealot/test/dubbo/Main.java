package group.zealot.test.dubbo;

import org.apache.dubbo.config.*;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
//@DubboComponentScan(basePackages = "group.zealot.test")
public class Main {
//    @Reference(interfaceClass = TestService.class, check = false)
//    public TestService testService;

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);
        Main main = context.getBean(Main.class);
//        main.testService.get();

        ServiceConfig<TestService> service = new ServiceConfig<TestService>(); // In case of memory leak, please cache.
        service.setApplication(applicationConfig());
        service.setRegistry(registryConfig()); // Use setRegistries() for multi-registry case
        service.setProtocol(protocolConfig()); // Use setProtocols() for multi-protocol case
        service.setInterface(TestService.class);
        service.setRef(new TestServiceImpl());
        service.setVersion("1.0.0");

// Local export and register
        service.export();

        ReferenceConfig<TestService> reference = new ReferenceConfig<TestService>(); // In case of memory leak, please cache.
        reference.setApplication(applicationConfig());
        reference.setRegistry(registryConfig());
        reference.setInterface(TestService.class);
        reference.setVersion("1.0.0");

// Use xxxService just like a local bean
        TestService testService = reference.get();
        testService.get();
    }

    //    @Bean
    public static ApplicationConfig applicationConfig() {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName("dubbo-test");
        return applicationConfig;
    }

    //    @Bean
    public static ProtocolConfig protocolConfig() {
        ProtocolConfig protocolConfig = new ProtocolConfig();
        protocolConfig.setName("dubbo");
        protocolConfig.setPort(20880);
        return protocolConfig;
    }

    //    @Bean
    public static RegistryConfig registryConfig() {
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress("zookeeper://127.0.0.1:2181");
        registryConfig.setClient("curator");
        return registryConfig;
    }

    //    @Bean
    public static ConsumerConfig consumerConfig() {
        ConsumerConfig consumerConfig = new ConsumerConfig();
        consumerConfig.setTimeout(3000);
        return consumerConfig;
    }

}
