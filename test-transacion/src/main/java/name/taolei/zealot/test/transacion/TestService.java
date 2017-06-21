package name.taolei.zealot.test.transacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestService {
    @Autowired
    private TestDao testDao;
    
    public void test() {
        testDao.saveA("1");
        testDao.saveA("2");
    }
}
