package name.taolei.zealot.test.springboot.mvcInnerTomcat.config.mybatis;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Component
public @interface Mapper {
    String value() default "";
}
