package name.taolei.zealot.test.springboot.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect @Component public class AopAspect {
    @Pointcut("@annotation(name.taolei.zealot.test.springboot.aop.Action)") public void annotationPointCut() {

    }

    @After("annotationPointCut()") public void after(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        Action action = method.getAnnotation(Action.class);
        System.out.println("after注解拦截1：" + action.name());
        System.out.println("after注解拦截2：" + method.getName());
    }

    @Before("execution(* name.taolei.zealot.test.springboot.aop.AopService.add(..))") public void before(
            JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        System.out.println("before方法拦截" + method.getName());
    }
}
