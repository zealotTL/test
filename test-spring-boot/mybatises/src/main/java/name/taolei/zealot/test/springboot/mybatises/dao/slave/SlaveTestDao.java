package name.taolei.zealot.test.springboot.mybatises.dao.slave;

import name.taolei.zealot.test.springboot.mybatises.entity.Test;
import name.taolei.zealot.test.springboot.mybatises.mapper.slave.SlaveTestMapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class SlaveTestDao extends SqlSessionDaoSupport implements SlaveTestMapper {

    private final String nameSpace = SlaveTestMapper.class.getName();

    @Autowired
    public void setSqlSessionFactory(@Qualifier("sqlSessionFactorySlave") SqlSessionFactory sqlSessionFactory) {
        super.setSqlSessionFactory(sqlSessionFactory);
    }

    public Test getById(int id) {
        return getSqlSession().selectOne(nameSpace + ".getById", id);
    }
    public void update(Test test) {
        getSqlSession().update(nameSpace + ".update", test);
    }

}
