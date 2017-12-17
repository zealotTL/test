package name.taolei.zealot.test.transacion;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TestDao extends SqlSessionDaoSupport implements TestMapper {
    
    @Autowired
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        super.setSqlSessionFactory(sqlSessionFactory);
    }
    
    public String getMapperNamesapce() {
        return TestMapper.class.getName().toString();
    }
    
    public void saveA(String id) {
        getSqlSession().insert(getMapperNamesapce() + ".insertA", id);
    }
    
}
