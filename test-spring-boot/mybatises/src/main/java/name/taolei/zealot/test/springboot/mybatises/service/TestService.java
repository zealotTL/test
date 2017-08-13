package name.taolei.zealot.test.springboot.mybatises.service;

import name.taolei.zealot.test.springboot.mybatises.dao.master.MasterTestDao;
import name.taolei.zealot.test.springboot.mybatises.dao.slave.SlaveTestDao;
import name.taolei.zealot.test.springboot.mybatises.entity.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(transactionManager = "transactionManagerSlave")
public class TestService {
    @Autowired private MasterTestDao masterTestDao;
    @Autowired private SlaveTestDao slaveTestDao;

    public String doSomething() {
        Test test = new Test();
        test.setId(1L);
        test.setA("1");
        slaveTestDao.update(test);
        System.out.println(test.getA());
        return "OK";
    }
}
