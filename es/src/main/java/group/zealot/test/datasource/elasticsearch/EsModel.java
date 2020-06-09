package group.zealot.test.datasource.elasticsearch;

import lombok.Data;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.IOException;
import java.util.Date;

/**
 * @author zealot
 * @date 2019/12/16 20:54
 */
@Data
public class EsModel {
    private String id;
    private String name;
    private int age;

    public static XContentBuilder generateBuilder() {
        try {
            XContentBuilder builder = XContentFactory.jsonBuilder();
            builder.startObject();
            {
                builder.startObject("properties");
                {
                    {
                        builder.startObject("id");
                        {
                            builder.field("type", "text");
                        }
                        builder.endObject();
                    }
                    builder.startObject("name");
                    {
                        builder.field("type", "text");
                        builder.field("analyzer", "ik_smart");
                    }
                    builder.endObject();
                }
                {
                    builder.startObject("age");
                    {
                        builder.field("type", "integer");
                    }
                    builder.endObject();
                }
                builder.endObject();
            }
            builder.endObject();
            return builder;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
