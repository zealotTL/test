package group.zealot.test.datasource.elasticsearch;

import lombok.Data;

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
    private Date date;
}
