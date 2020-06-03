package group.zealot.test.datasource.elasticsearch;

import com.alibaba.fastjson.JSONObject;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author zealot
 * @date 2019/12/17 20:30
 */
@Component
public class BaseElasticService {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    /**
     * @param indexName   索引名称
     * @param indexSource 索引描述
     */
    public void createIndex(String indexName, String indexSource) {
        try {
            CreateIndexRequest request = new CreateIndexRequest(indexName);
            buildSetting(request);
            request.mapping(indexSource, XContentType.JSON);
            CreateIndexResponse res = restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);
            if (!res.isAcknowledged()) {
                throw new RuntimeException("初始化失败");
            }
        } catch (Exception e) {
            logger.error("indexName={} indexSource={}", indexName, indexSource);
            throw new RuntimeException(e);
        }
    }

    /**
     * 断某个index是否存在
     *
     * @param indexName index名
     */
    public boolean indexExist(String indexName) {
        GetIndexRequest request = new GetIndexRequest(indexName);
        request.local(false);
        request.humanReadable(true);
        request.includeDefaults(false);
        request.indicesOptions(IndicesOptions.lenientExpandOpen());
        try {
            return restHighLevelClient.indices().exists(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            logger.error("indexName={}", indexName);
            throw new RuntimeException(e);
        }
    }

    /**
     * 断某个index是否存在
     *
     * @param indexName index名
     */
    public boolean isExistsIndex(String indexName) {
        try {
            return restHighLevelClient.indices().exists(new GetIndexRequest(indexName), RequestOptions.DEFAULT);
        } catch (IOException e) {
            logger.error("indexName={}", indexName);
            throw new RuntimeException(e);
        }
    }

    /**
     * 设置分片
     *
     * @param request
     */
    public void buildSetting(CreateIndexRequest request) {
        request.settings(Settings.builder().put("index.number_of_shards", 3)
                .put("index.number_of_replicas", 2));
    }

    /**
     * @param indexName index
     * @param entity    对象
     */
    public void insertOrUpdateOne(String indexName, EsModel entity) {
        IndexRequest request = new IndexRequest(indexName);
        logger.error("Data : id={},entity={}", entity.getId(), JSONObject.toJSONString(entity));
        request.id(entity.getId());
        request.source(entity, XContentType.JSON);
        try {
            restHighLevelClient.index(request, RequestOptions.DEFAULT);
        } catch (Exception e) {
            logger.error("indexName={} entity={}", indexName, entity);
            throw new RuntimeException(e);
        }
    }


    /**
     * 批量插入数据
     *
     * @param indexName index
     * @param list      带插入列表
     */
    public void insertBatch(String indexName, List<EsModel> list) {
        BulkRequest request = new BulkRequest();
        list.forEach(item -> request.add(new IndexRequest(indexName).id(item.getId())
                .source(item, XContentType.JSON)));
        try {
            restHighLevelClient.bulk(request, RequestOptions.DEFAULT);
        } catch (Exception e) {
            logger.error("indexName={} list={}", indexName, list);
            throw new RuntimeException(e);
        }
    }

    /**
     * 批量删除
     *
     * @param indexName index
     * @param idList    待删除列表
     */
    public <T> void deleteBatch(String indexName, Collection<T> idList) {
        BulkRequest request = new BulkRequest();
        idList.forEach(item -> request.add(new DeleteRequest(indexName, item.toString())));
        try {
            restHighLevelClient.bulk(request, RequestOptions.DEFAULT);
        } catch (Exception e) {
            logger.error("indexName={} list={}", indexName, idList);
            throw new RuntimeException(e);
        }
    }

    /**
     * @param indexName index
     * @param builder   查询参数
     * @param clazz     结果类对象
     */
    public <T> List<T> search(String indexName, SearchSourceBuilder builder, Class<T> clazz) {
        SearchRequest request = new SearchRequest(indexName);
        request.source(builder);
        try {
            SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
            SearchHit[] hits = response.getHits().getHits();
            List<T> res = new ArrayList<>(hits.length);
            for (SearchHit hit : hits) {
                res.add(JSONObject.parseObject(hit.getSourceAsString(), clazz));
            }
            return res;
        } catch (Exception e) {
            logger.error("indexName={} builder={} clazz={}", indexName, builder, clazz);
            throw new RuntimeException(e);
        }
    }

    /**
     * 删除index
     *
     * @param indexName
     */
    public void deleteIndex(String indexName) {
        try {
            if (!this.indexExist(indexName)) {
                logger.error(" indexName={} 已经存在", indexName);
                return;
            }
            restHighLevelClient.indices().delete(new DeleteIndexRequest(indexName), RequestOptions.DEFAULT);
        } catch (Exception e) {
            logger.error("indexName={} ", indexName);
            throw new RuntimeException(e);
        }
    }


    /**
     * @param indexName
     * @param builder
     */
    public void deleteByQuery(String indexName, QueryBuilder builder) {

        DeleteByQueryRequest request = new DeleteByQueryRequest(indexName);
        request.setQuery(builder);
        //设置批量操作数量,最大为10000
        request.setBatchSize(10000);
        request.setConflicts("proceed");
        try {
            restHighLevelClient.deleteByQuery(request, RequestOptions.DEFAULT);
        } catch (Exception e) {
            logger.error("indexName={} builder={}", indexName, builder);
            throw new RuntimeException(e);
        }
    }
}
