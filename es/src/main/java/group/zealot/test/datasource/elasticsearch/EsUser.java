package group.zealot.test.datasource.elasticsearch;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * @author zealot
 * @date 2019/12/16 20:54
 */
@Data
@Document(indexName = "es_user")
public class EsUser {
    @Id
    private String id;
    private String name;
    private int age;
}
