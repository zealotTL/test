package name.taolei.zealot.test.springboot.mybatis.dao;

import name.taolei.zealot.test.springboot.mybatis.config.Mapper;
import name.taolei.zealot.test.springboot.mybatis.entity.Test;

@Mapper
public interface TestDao {
    Test getById(int id);

    void update(Test test);
}
