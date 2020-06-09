package group.zealot.test.datasource.elasticsearch;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author zealot
 * @date 2020/6/9 22:29
 */
public interface EsUserService extends ElasticsearchRepository<EsUser, String> {
}
