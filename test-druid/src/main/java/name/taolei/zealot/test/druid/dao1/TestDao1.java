package name.taolei.zealot.test.druid.dao1;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import name.taolei.zealot.test.druid.mapper1.TestMapper1;

@Repository
public class TestDao1 extends SqlSessionDaoSupport implements TestMapper1 {
    
    @Autowired
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        super.setSqlSessionFactory(sqlSessionFactory);
    }
    
    public String getMapperNamesapce() {
        return TestMapper1.class.getName().toString();
    }
    
    public void saveA(String id) {
        getSqlSession().insert(getMapperNamesapce() + ".insertA", id);
    }
    
}
