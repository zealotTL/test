package name.taolei.zealot.test.druid.dao2;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import name.taolei.zealot.test.druid.mapper2.TestMapper2;

@Repository("testDao2")
public class TestDao2 extends SqlSessionDaoSupport implements TestMapper2 {
    
    @Autowired
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        super.setSqlSessionFactory(sqlSessionFactory);
    }
    
    public String getMapperNamesapce() {
        return TestMapper2.class.getName().toString();
    }
    
    public void saveA(String id) {
        getSqlSession().insert(getMapperNamesapce() + ".insertA", id);
    }
    
}
