package name.taolei.zealot.test.springboot.mvc.dao;

import name.taolei.zealot.test.springboot.mvc.config.Mapper;
import name.taolei.zealot.test.springboot.mvc.entity.Test;

@Mapper
public interface TestDao {
    Test getById(int id);

    void update(Test test);
}
