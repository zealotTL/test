package group.zealot.test;

import com.alibaba.fastjson.JSONObject;
import group.zealot.test.datasource.Main;
import group.zealot.test.datasource.elasticsearch.BaseElasticService;
import group.zealot.test.datasource.elasticsearch.EsModel;
import group.zealot.test.datasource.elasticsearch.EsUser;
import group.zealot.test.datasource.elasticsearch.EsUserService;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Main.class})
@Component
public class TestMain {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    BaseElasticService baseElasticService;

    @Test
    public void es() {
        String indexName = "test";

        if (!baseElasticService.isExistsIndex(indexName)) {
            baseElasticService.createIndex(indexName, EsModel.generateBuilder());

        }
        EsModel esModel = new EsModel();
        esModel.setId("1111");
        esModel.setAge(19);
        baseElasticService.insertOrUpdateOne(indexName, esModel);

        SearchSourceBuilder builder = new SearchSourceBuilder();
        List<EsModel> list = baseElasticService.search(indexName, builder, EsModel.class);
        logger.info(list.size() + "");
    }

    @Autowired(required = false)
    EsUserService esUserService;

    @Test
    public void esUser() {
        Iterable iterable = esUserService.findAll();
        iterable.forEach(item -> logger.info(JSONObject.toJSONString(item)));
        EsUser vo = new EsUser();
        vo.setId("12345");
        vo.setAge(20);
        vo.setName("陶磊123");
        esUserService.save(vo);
        iterable = esUserService.findAll();
        iterable.forEach(item -> logger.info(JSONObject.toJSONString(item)));
    }
}
