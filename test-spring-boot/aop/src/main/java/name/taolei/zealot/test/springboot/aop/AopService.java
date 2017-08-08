package name.taolei.zealot.test.springboot.aop;

import org.springframework.stereotype.Service;

@Service
@Action(name="AopService")
public class AopService {
    @Action(name="add")
    public void add(){
        System.out.println("执行add");
    }
}
