package name.taolei.zealot.test.springboot.mybatises.dao.master;

import name.taolei.zealot.test.springboot.mybatises.entity.Test;
import name.taolei.zealot.test.springboot.mybatises.mapper.master.MasterTestMapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class MasterTestDao extends SqlSessionDaoSupport implements MasterTestMapper {
    //sqlSessionFactoryMaster
    private final String nameSpace = MasterTestMapper.class.getName();

    @Autowired
    public void setSqlSessionFactory(@Qualifier("sqlSessionFactoryMaster") SqlSessionFactory sqlSessionFactory) {
        super.setSqlSessionFactory(sqlSessionFactory);
    }

    public Test getById(int id) {
        return getSqlSession().selectOne(nameSpace + ".getById", id);
    }
}
