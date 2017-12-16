package name.taolei.zealot.test.springboot.mvcInnerTomcat.dao;

import name.taolei.zealot.test.springboot.mvcInnerTomcat.config.mybatis.Mapper;
import name.taolei.zealot.test.springboot.mvcInnerTomcat.entity.Test;

@Mapper
public interface TestDao {
    Test getById(int id);

    void update(Test test);
}
