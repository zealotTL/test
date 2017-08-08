package name.taolei.zealot.test.springboot.async;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AsyncTaskService {

    @Async
    public void sysAsync(Object o){
        System.out.println(o);
    }

    public void sys(Object o){
        System.out.println(o);
    }
}
