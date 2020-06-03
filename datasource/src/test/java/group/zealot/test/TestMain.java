package group.zealot.test;

import com.alibaba.fastjson.JSONObject;
import com.mongodb.client.result.UpdateResult;
import group.zealot.test.datasource.Main;
import group.zealot.test.datasource.elasticsearch.BaseElasticService;
import group.zealot.test.datasource.elasticsearch.EsModel;
import group.zealot.test.datasource.elasticsearch.EsPage;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Main.class})
@Component
public class TestMain {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    ApplicationContext applicationContext;

    @Autowired(required = false)
    private MongoTemplate mongoTemplate;

    @Test
    public void send() throws InterruptedException {
        if (!mongoTemplate.collectionExists("user")) {
            logger.info("创建 user 集合");
            mongoTemplate.createCollection("user");
        }

        List<JSONObject> list = mongoTemplate.findAll(JSONObject.class, "user");

        Query query = Query.query(Criteria.where("name").is("tao"));

        JSONObject tao = mongoTemplate.findOne(query, JSONObject.class, "user");

        Update update = Update.update("age", 15);
        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, "user");
/*
        tao = new JSONObject();
        tao.put("name", "tao");
        tao.put("age", 17);
        tao.put("sex", "男");
        mongoTemplate.insert(tao, "user");
*/

        logger.info("over");
    }

    @Autowired
    BaseElasticService baseElasticService;

    @Test
    public void es() {
        String indexName = "test";
        String esType = "external";
        String id;
        if (!baseElasticService.isExistsIndex(indexName)) {
            baseElasticService.createIndex(indexName, "仅用于测试");

        }
    }


}
